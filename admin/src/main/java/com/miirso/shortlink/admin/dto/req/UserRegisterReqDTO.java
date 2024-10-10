package com.miirso.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @Package com.miirso.shortlink.admin.dto.req
 * @Author miirso
 * @Date 2024/10/11 1:53
 *
 * 用户注册request参数
 *
 */

@Data
public class UserRegisterReqDTO {

    private String username;

    private String password;

    private String realName;

    private String phone;

    private String mail;

}
