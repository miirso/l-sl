package com.miirso.shortlink.project.controller;

import com.miirso.shortlink.project.common.convention.result.Result;
import com.miirso.shortlink.project.common.convention.result.Results;
import com.miirso.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.miirso.shortlink.project.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        recycleBinService.saveRecycleBin(recycleBinSaveReqDTO);
        return Results.success();
    }

}
