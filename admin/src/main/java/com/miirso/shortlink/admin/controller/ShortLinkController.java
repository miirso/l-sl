package com.miirso.shortlink.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miirso.shortlink.admin.client.LinkClient;
import com.miirso.shortlink.admin.common.convention.result.Result;
import com.miirso.shortlink.admin.dto.resp.ShortLinkGroupCountRespDTO;
import com.miirso.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.miirso.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.miirso.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.miirso.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.miirso.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/short-link/admin/v1") // v1作为最后后缀
@RequiredArgsConstructor
public class ShortLinkController {

    private final LinkClient linkClient;

    @GetMapping("/page")
    public Result<Page<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        System.out.println("hello");
        // ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {};
        // return shortLinkRemoteService.pageShortLink(shortLinkPageReqDTO);
        Result<Page<ShortLinkPageRespDTO>> pageResult = linkClient.pageShortLink(
                shortLinkPageReqDTO.getGid(),
                shortLinkPageReqDTO.getCurrent(),
                shortLinkPageReqDTO.getSize(),
                shortLinkPageReqDTO.getOrderTag()
                );

        return pageResult;
    }

    @PostMapping("/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        System.out.println(requestParam.getGid());
        return linkClient.updateShortLink(requestParam);
    }

    @PostMapping("/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO reqDTO) {
        return linkClient.createShortLink(reqDTO);
    }

    @GetMapping("/count")
    Result<List<ShortLinkGroupCountRespDTO>> listGroupShortLinkCount(@RequestParam("gids") List<String> gids) {
        return linkClient.listGroupShortLinkCount(gids);
    }
}
