package com.miirso.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miirso.shortlink.admin.dao.entity.UserDO;
import com.miirso.shortlink.admin.dao.mapper.UserMapper;
import com.miirso.shortlink.admin.dto.resp.UserRespDTO;
import com.miirso.shortlink.admin.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    public UserRespDTO getUserByUserName(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        UserRespDTO res = new UserRespDTO();
        BeanUtils.copyProperties(userDO, res);
        return res;
    }
}
