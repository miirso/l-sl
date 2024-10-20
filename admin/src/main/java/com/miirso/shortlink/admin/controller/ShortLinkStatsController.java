package com.miirso.shortlink.admin.controller;

import com.miirso.shortlink.admin.client.LinkClient;
import com.miirso.shortlink.admin.common.convention.result.Result;
import com.miirso.shortlink.admin.remote.dto.req.ShortLinkStatsReqDTO;
import com.miirso.shortlink.admin.remote.dto.resp.ShortLinkStatsRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package com.miirso.shortlink.admin.controller
 * @Author miirso
 * @Date 2024/10/20 21:28
 */

@RestController
@RequestMapping("/api/short-link/admin/v1")
@RequiredArgsConstructor
public class ShortLinkStatsController {

    private final LinkClient linkClient;

    @GetMapping("/stats")
    public Result<ShortLinkStatsRespDTO> shortLinkStats(ShortLinkStatsReqDTO shortLinkStatsReqDTO) {
        return linkClient.shortLinkStats(
                shortLinkStatsReqDTO.getFullShortUrl(),
                shortLinkStatsReqDTO.getGid(),
                shortLinkStatsReqDTO.getStartDate(),
                shortLinkStatsReqDTO.getEndDate()
        );
    }
}
