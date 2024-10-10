package com.miirso.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @Package com.miirso.shortlink.admin.dto.resp
 * @Author miirso
 * @Date 2024/10/10 22:35
 *
 * User Response DTO
 * 用户返回参数响应实体
 */

@Data
public class UserRespDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 团队标识
     */
    private Long groupId;

}
