package com.miirso.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miirso.shortlink.admin.common.convention.exception.ServiceException;
import com.miirso.shortlink.admin.common.enums.UserErrorCode;
import com.miirso.shortlink.admin.dao.entity.UserDO;
import com.miirso.shortlink.admin.dao.mapper.UserMapper;
import com.miirso.shortlink.admin.dto.resp.UserRespDTO;
import com.miirso.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public UserRespDTO getUserByUserName(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        // 规范中Controller层不处理异常,下放到Service层处理.
        if (userDO == null) {
            throw new ServiceException(UserErrorCode.USER_NULL);
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
        // return userDO == null;
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }



}
