package com.miirso.shortlink.admin.remote.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Package com.miirso.shortlink.project.dto.req
 * @Author miirso
 * @Date 2024/10/13 15:32
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkCreateReqDTO {

    /**
     * 域名
     */
    private String domain;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 创建类型 0：控制台 1：接口
     */
    private int createdType;

    /**
     * 有效期类型 0：永久有效 1：用户自定义
     */
    private int validDateType;

    /**
     * 有效期
     */
    private Date validDate;

    /**
     * 描述
     */
    private String describe;

}
