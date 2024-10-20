package com.miirso.shortlink.admin.remote.dto.req;

import lombok.Data;

/**
 * @Package com.miirso.shortlink.project.dto.req
 * @Author miirso
 * @Date 2024/10/20 19:24
 *
 * 短链接监控请求参数 DTO
 *
 */

@Data
public class ShortLinkStatsReqDTO {

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

}
