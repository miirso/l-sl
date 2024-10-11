package com.miirso.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miirso.shortlink.admin.dao.entity.UserDO;
import com.miirso.shortlink.admin.dto.req.UserLoginReqDTO;
import com.miirso.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.miirso.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.miirso.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.miirso.shortlink.admin.dto.resp.UserRegisterRespDTO;
import com.miirso.shortlink.admin.dto.resp.UserRespDTO;
import jakarta.servlet.http.HttpServletRequest;

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

    /**
     * 用户注册
     * @param userRegisterReqDTO
     */
    UserRegisterRespDTO register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 用户信息修改
     * @param userUpdateReqDTO
     */
    void update(UserUpdateReqDTO userUpdateReqDTO);

    /**
     * 用户登录
     * @param userLoginReqDTO
     * @return UserLoginRespDTO
     */
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    /**
     * 检查是否处于登录状态
     * @return Boolean
     */
    Boolean check(HttpServletRequest request);

    /**
     * 退出登录
     * @param request
     * @return Boolean
     */
    Boolean logout(HttpServletRequest request);
}
