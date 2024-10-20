package com.miirso.shortlink.project.controller;

import com.miirso.shortlink.project.common.convention.result.Result;
import com.miirso.shortlink.project.common.convention.result.Results;
import com.miirso.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkStatsRespDTO;
import com.miirso.shortlink.project.service.ShortLinkStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package com.miirso.shortlink.project.controller
 * @Author miirso
 * @Date 2024/10/20 19:21
 *
 * 短链接监控
 *
 */

@RestController
@RequestMapping("/api/short-link/project/v1/stats")
@RequiredArgsConstructor
public class ShortLinkStatsController {

    private final ShortLinkStatsService shortLinkStatsService;

    @GetMapping
    public Result<ShortLinkStatsRespDTO> shortLinkStats(ShortLinkStatsReqDTO shortLinkStatsReqDTO) {
        return Results.success(shortLinkStatsService.oneShortLinkStats(shortLinkStatsReqDTO));
    }
}
