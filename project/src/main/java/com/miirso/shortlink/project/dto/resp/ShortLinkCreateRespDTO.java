package com.miirso.shortlink.project.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @Package com.miirso.shortlink.project.dto.resp
 * @Author miirso
 * @Date 2024/10/13 15:38
 */
@Data
@Builder
public class ShortLinkCreateRespDTO {

    /**
     * 分组信息
     */
    // private ShortLinkGroupRespDTO shortLinkGroupRespDTO;
    private String gid;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

}
