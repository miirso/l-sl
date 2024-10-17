package com.miirso.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miirso.shortlink.project.common.convention.result.Result;
import com.miirso.shortlink.project.common.convention.result.Results;
import com.miirso.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.miirso.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.miirso.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkGroupCountRespDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.miirso.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 短链接创建
     * @param reqDTO
     */
    @PostMapping("/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO reqDTO) {
        ShortLinkCreateRespDTO respDTO = shortLinkService.createShortLink(reqDTO);
        return Results.success(respDTO);
    }

    /**
     * 短链接分组查询
     * @param shortLinkPageReqDTO
     */
    @GetMapping("/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        return Results.success(shortLinkService.pageShortLink(shortLinkPageReqDTO));
    }

    /**
     * 短链接分组中链接数量查询
     * @param gids
     */
    @GetMapping("/count")
    public Result<List<ShortLinkGroupCountRespDTO>> listGroupShortLinkCount(@RequestParam("gids") List<String> gids) {
        return Results.success(shortLinkService.listGroupShortLinkCount(gids));
    }

    /**
     * 修改短链接中信息
     * @param requestParam
     */
    @PostMapping("/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        System.out.println(requestParam.getGid());
        shortLinkService.updateShortLink(requestParam);
        return Results.success();
    }

}
