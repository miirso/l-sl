package com.miirso.shortlink.project.service;

import com.miirso.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkStatsRespDTO;

/**
 * @Package com.miirso.shortlink.project.service
 * @Author miirso
 * @Date 2024/10/20 21:13
 */

public interface ShortLinkStatsService {

    /**
     * 获取单个短链接监控数据
     * @param requestParam 获取短链接监控数据入参
     * @return 短链接监控数据
     */
    ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam);

}
