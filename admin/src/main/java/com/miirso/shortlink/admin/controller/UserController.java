package com.miirso.shortlink.admin.controller;

import com.miirso.shortlink.admin.common.convention.result.Result;
import com.miirso.shortlink.admin.common.convention.result.Results;
import com.miirso.shortlink.admin.dto.req.UserLoginReqDTO;
import com.miirso.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.miirso.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.miirso.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.miirso.shortlink.admin.dto.resp.UserRegisterRespDTO;
import com.miirso.shortlink.admin.dto.resp.UserRespDTO;
import com.miirso.shortlink.admin.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.miirso.shortlink.admin.controller
 * @Author miirso
 * @Date 2024/10/10 22:30
 *
 * UserController
 *
 */

@RestController
@RequestMapping("/api/short-link/admin/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据用户名查询用户基本信息
     * @param username
     * @return UserRespDTO 用户基本信息
     */
    @GetMapping("{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable String username) {
        return Results.success(userService.getUserByUserName(username));
    }

    /**
     * 查询用户名是否存在
     * @param username
     * @return Boolean
     */
    @GetMapping("/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        return Results.success(userService.hasUserName(username));
    }

    /**
     * 注册用户
     * @param userRegisterReqDTO
     * @return Void
     */
    @PostMapping
    public Result<UserRegisterRespDTO> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
        UserRegisterRespDTO userRegisterRespDTO = userService.register(userRegisterReqDTO);
        return Results.success(userRegisterRespDTO);
    }

    /**
     * 修改用户信息
     * @param userUpdateReqDTO
     * @return Void
     */
    @PutMapping
    public Result<Void> update(@RequestBody UserUpdateReqDTO userUpdateReqDTO) {
        userService.update(userUpdateReqDTO);
        return Results.success();
    }

    /**
     * 用户登录
     * @param userLoginReqDTO
     * @return UserLoginRespDTO
     */
    @PostMapping("/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        UserLoginRespDTO userLoginRespDTO = userService.login(userLoginReqDTO);
        return Results.success(userLoginRespDTO);
    }

    /**
     * 检查登录状态
     * @param request
     * @return Boolean
     */
    @GetMapping("check")
    public Result<Boolean> check(HttpServletRequest request) {
        Boolean isLogin = userService.check(request);
        return Results.success(isLogin);
    }

    @DeleteMapping("logout")
    public Result<Boolean> logout(HttpServletRequest request) {
        Boolean isLogout = userService.logout(request);
        return Results.success(isLogout);
    }

}
