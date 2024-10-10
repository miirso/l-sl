package com.miirso.shortlink.admin.controller;

import com.miirso.shortlink.admin.common.convention.result.Result;
import com.miirso.shortlink.admin.common.convention.result.Results;
import com.miirso.shortlink.admin.dto.resp.UserRespDTO;
import com.miirso.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package com.miirso.shortlink.admin.controller
 * @Author miirso
 * @Date 2024/10/10 22:30
 *
 * UserController
 *
 */

@RestController
@RequestMapping("/api/shortlink/v1/user")
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


}
