package com.miirso.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @Package com.miirso.shortlink.admin.dto.req
 * @Author miirso
 * @Date 2024/10/11 4:07
 */
@Data
public class UserUpdateReqDTO {

    private String username;

    private String password;

    private String realName;

    private String phone;

    private String mail;
}
