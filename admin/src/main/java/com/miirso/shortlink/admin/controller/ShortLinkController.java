package com.miirso.shortlink.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miirso.shortlink.admin.common.convention.result.Result;
import com.miirso.shortlink.admin.remote.dto.ShortLinkRemoteService;
import com.miirso.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.miirso.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/short-link/admin/v1") // v1作为最后后缀
@RequiredArgsConstructor
public class ShortLinkController {

    @GetMapping("/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {};
        return shortLinkRemoteService.pageShortLink(shortLinkPageReqDTO);
    }
}
