package com.miirso.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @Package com.miirso.shortlink.admin.dto.resp
 * @Author miirso
 * @Date 2024/10/11 21:50
 */

@Data
public class GroupRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 创建分组用户名
     */
    private String username;

    /**
     * 分组排序
     */
    private Integer sortOrder;

    /**
     * 当前分组下短链接的数量
     */
    private Long sortLinkCount;
}
