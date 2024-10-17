package com.miirso.shortlink.project.controller;

import com.miirso.shortlink.project.service.ShortLinkService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package com.miirso.shortlink.project.controller
 * @Author miirso
 * @Date 2024/10/17 18:42
 */

@RestController // v1作为最后后缀
@RequiredArgsConstructor
public class ShortLinkGotoController {

    private final ShortLinkService shortLinkService;

    /**
     * 短链接跳转
     */
    @GetMapping("/{short-uri}")
    public void restoreUrl(@PathVariable("short-uri") String shortUri, ServletRequest servletRequest, ServletResponse servletResponse) {
        shortLinkService.restoreUrl(shortUri, servletRequest, servletResponse);
    }

}
