package com.miirso.shortlink.project.dto.req;

import lombok.Data;

/**
 * @Package com.miirso.shortlink.project.dto.req
 * @Author miirso
 * @Date 2024/10/17 20:32
 *
 * 回收站保存新链接参数DTO
 *
 */
@Data
public class RecycleBinSaveReqDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 短链接索引
     */
    private String fullShortUrl;

}
