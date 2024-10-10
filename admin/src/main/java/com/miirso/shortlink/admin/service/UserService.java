package com.miirso.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miirso.shortlink.admin.dao.entity.UserDO;
import com.miirso.shortlink.admin.dto.resp.UserRespDTO;

/**
 * @Package com.miirso.shortlink.admin.service
 * @Author miirso
 * @Date 2024/10/10 22:27
 *
 * 用户接口层
 *
 */

public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return UserRespDTO 用户基本信息
     */
    UserRespDTO getUserByUserName(String username);

    /**
     * 查看用户名是否存在
     * @param username
     * @return true or false
     */
    Boolean hasUserName(String username);
}
