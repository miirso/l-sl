package com.miirso.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miirso.shortlink.admin.common.constant.RedisCacheConstant;
import com.miirso.shortlink.admin.common.convention.exception.ClientException;
import com.miirso.shortlink.admin.common.convention.exception.ServiceException;
import com.miirso.shortlink.admin.common.enums.UserErrorCode;
import com.miirso.shortlink.admin.dao.entity.UserDO;
import com.miirso.shortlink.admin.dao.mapper.UserMapper;
import com.miirso.shortlink.admin.dto.req.GroupSaveReqDTO;
import com.miirso.shortlink.admin.dto.req.UserLoginReqDTO;
import com.miirso.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.miirso.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.miirso.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.miirso.shortlink.admin.dto.resp.UserRegisterRespDTO;
import com.miirso.shortlink.admin.dto.resp.UserRespDTO;
import com.miirso.shortlink.admin.dto.thread.UserInfoDTO;
import com.miirso.shortlink.admin.service.GroupService;
import com.miirso.shortlink.admin.service.UserService;
import com.miirso.shortlink.admin.utils.PasswordEncoder;
import com.miirso.shortlink.admin.utils.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.miirso.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static com.miirso.shortlink.admin.common.constant.RedisCacheConstant.LOGIN_USER_TTL;
import static com.miirso.shortlink.admin.common.enums.UserErrorCode.*;

/**
 * @Package com.miirso.shortlink.admin.service.impl
 * @Author miirso
 * @Date 2024/10/10 22:28
 *
 * 用户接口实现层
 *
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    private final RedissonClient redissonClient;

    private final StringRedisTemplate stringRedisTemplate;

    private final GroupService groupService;

    @Override
    public UserRespDTO getUserByUserName(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        // 规范中Controller层不处理异常,下放到Service层处理.
        if (userDO == null) {
            throw new ClientException(UserErrorCode.USER_NULL);
        }
        UserRespDTO res = new UserRespDTO();
        BeanUtils.copyProperties(userDO, res);
        return res;
    }

    @Override
    public Boolean hasUserName(String username) {
        // LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
        //         .eq(UserDO::getUsername, username);
        // UserDO userDO = baseMapper.selectOne(queryWrapper);
        // return !(userDO == null);

        // 布隆过滤器.
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    // TODO 添加一个功能，用户注册后自动生成一个短链接分组，作为用户的默认分组
    public UserRegisterRespDTO register(UserRegisterReqDTO userRegisterReqDTO) {
        String username = userRegisterReqDTO.getUsername();
        if (!hasUserName(username)) {
            throw new ClientException(UserErrorCode.USER_NAME_EXIST);
        }
        // 分布式锁:防止瞬时大量注册请求.
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + username);
        try {
            if (lock.tryLock()) {
                // 加密password
                UserDO user = BeanUtil.toBean(userRegisterReqDTO, UserDO.class);
                user.setPassword(PasswordEncoder.encode(user.getPassword()));
                // TODO 新增用户重复会报错,需要修改
                int inserted = baseMapper.insert(user);
                if (inserted < 1) {
                    throw new ServiceException(UserErrorCode.USER_SAVE_ERROR);
                }

                userRegisterCachePenetrationBloomFilter.add(username);

                // 生成token
                String token = UUID.randomUUID().toString();
                String tokenKey = RedisCacheConstant.LOGIN_USER_KEY + token;
                // 60min ttl
                stringRedisTemplate.opsForValue().set(tokenKey, username, LOGIN_USER_TTL, TimeUnit.MINUTES);

                // 当前线程存入User信息
                UserInfoDTO userInfoDTO = BeanUtil.toBean(userRegisterReqDTO, UserInfoDTO.class);
                Long userId = user.getId();
                System.out.println(userId);
                userInfoDTO.setUserId(userId);
                userInfoDTO.setToken(token);
                UserHolder.saveUser(userInfoDTO);

                // 为当前用户生成默认分组
                GroupSaveReqDTO groupSaveReqDTO = new GroupSaveReqDTO("default");
                groupService.saveGroup(groupSaveReqDTO);

                return new UserRegisterRespDTO(token);
            }
            throw new ClientException(USER_NAME_EXIST);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void update(UserUpdateReqDTO userUpdateReqDTO) {
        // TODO 验证当前用户名是否为登录用户.
        // 到这一步是拥有token的，但是需要做校验，防止修改他人信息.
        // 留到网关部分fix
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, userUpdateReqDTO.getUsername());
        baseMapper.update(BeanUtil.toBean(userUpdateReqDTO, UserDO.class), updateWrapper);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO) {
        String username = userLoginReqDTO.getUsername();
        if (username == null) throw new ClientException(USER_NAME_ERROR);
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);

        if (userDO == null) {
            throw new ClientException(USER_NULL);
        }

        if (!BCrypt.checkpw(userLoginReqDTO.getPassword(), userDO.getPassword())) {
            throw new ClientException(USER_LOGIN_ERROR);
        }

        // redis 存储token
        // 生成token
        String token = UUID.randomUUID().toString();
        String tokenKey = RedisCacheConstant.LOGIN_USER_KEY + token;
        // 60min ttl
        stringRedisTemplate.opsForValue().set(tokenKey, username, LOGIN_USER_TTL, TimeUnit.MINUTES);
        UserInfoDTO userInfoDTO = new UserInfoDTO(userDO.getId(),
                userDO.getUsername(),
                userDO.getRealName(),
                token);
        UserHolder.saveUser(userInfoDTO);

        return new UserLoginRespDTO(token);
    }

    @Override
    public Boolean check(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        String tokenKey = RedisCacheConstant.LOGIN_USER_KEY + token;
        System.out.println(tokenKey);
        String username = stringRedisTemplate.opsForValue().get(tokenKey);
        return !(username == null);
    }

    @Override
    public Boolean logout(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        String tokenKey = RedisCacheConstant.LOGIN_USER_KEY + token;
        Boolean isDeleted = stringRedisTemplate.delete(tokenKey);
        return isDeleted;
    }
}
