package com.miirso.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miirso.shortlink.project.common.convention.result.Result;
import com.miirso.shortlink.project.common.convention.result.Results;
import com.miirso.shortlink.project.dto.req.RecycleBinPageReqDTO;
import com.miirso.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.miirso.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.miirso.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.miirso.shortlink.project.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.miirso.shortlink.project.controller
 * @Author miirso
 * @Date 2024/10/17 20:30
 */

@RestController
@RequestMapping("/api/short-link/project/v1/recycle-bin")
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    /**
     * 短链接移至回收站
     * @param recycleBinSaveReqDTO
     * @return
     */
    @PostMapping("/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        recycleBinService.saveRecycleBin(recycleBinSaveReqDTO);
        return Results.success();
    }

    /**
     * 回收站分页查询
     * @param reqDTO
     * @return Result-IPage-ShortLinkPageRespDTO--
     */
    @GetMapping("/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycledShortLink(RecycleBinPageReqDTO reqDTO) {
        return Results.success(recycleBinService.pageShortLink(reqDTO));
    }

    /**
     * 回收站移除指定链接
     * @param requestParam
     * @return Result-void
     */
    @PostMapping("/recover")
    public Result<Void> recoverShortLink(@RequestBody RecycleBinRecoverReqDTO requestParam) {
        recycleBinService.recoverShortLink(requestParam);
        return Results.success();
    }

}
