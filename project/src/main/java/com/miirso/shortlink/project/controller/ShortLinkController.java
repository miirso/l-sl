package com.miirso.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miirso.shortlink.project.common.convention.result.Result;
import com.miirso.shortlink.project.common.convention.result.Results;
import com.miirso.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.miirso.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.miirso.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.miirso.shortlink.project.controller
 * @Author miirso
 * @Date 2024/10/13 15:20
 *
 * 短链接控制层
 *
 */

@RestController
@RequestMapping("/api/short-link/project/v1") // v1作为最后后缀
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @PostMapping("/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO reqDTO) {
        ShortLinkCreateRespDTO respDTO = shortLinkService.createShortLink(reqDTO);
        return Results.success(respDTO);
    }

    @GetMapping("/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {

        return Results.success(shortLinkService.pageShortLink(shortLinkPageReqDTO));
    }
}
