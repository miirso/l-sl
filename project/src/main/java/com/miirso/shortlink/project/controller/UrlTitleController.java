package com.miirso.shortlink.project.controller;

import com.miirso.shortlink.project.common.convention.result.Result;
import com.miirso.shortlink.project.common.convention.result.Results;
import com.miirso.shortlink.project.service.UrlTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Package com.miirso.shortlink.project.controller
 * @Author miirso
 * @Date 2024/10/17 20:11
 *
 * URL 标题控制层
 *
 */

@RestController
@RequestMapping("/api/short-link/project/v1")
@RequiredArgsConstructor
public class UrlTitleController {

    private final UrlTitleService urlTitleService;

    @GetMapping("/title")
    public Result<String> getUrlTitle(@RequestParam("url") String url) throws IOException {
        return Results.success(urlTitleService.getUrlTitle(url));
    }

}
