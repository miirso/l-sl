package com.miirso.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @Package com.miirso.shortlink.admin.dto.req
 * @Author miirso
 * @Date 2024/10/11 4:21
 */

@Data
public class UserLoginReqDTO {

    private String username;

    private String password;

}
