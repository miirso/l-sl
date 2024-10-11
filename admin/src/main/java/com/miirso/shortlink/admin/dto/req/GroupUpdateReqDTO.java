package com.miirso.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @Package com.miirso.shortlink.admin.dto.req
 * @Author miirso
 * @Date 2024/10/11 22:28
 *
 * 短链接分组信息修改DTO
 *
 */

@Data
public class GroupUpdateReqDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名
     */
    private String name;

}
