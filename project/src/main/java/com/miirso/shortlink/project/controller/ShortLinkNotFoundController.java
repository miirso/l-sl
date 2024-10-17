package com.miirso.shortlink.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Package com.miirso.shortlink.project.controller
 * @Author miirso
 * @Date 2024/10/17 20:02
 *
 *
 *
 */

@Controller
public class ShortLinkNotFoundController {

    /**
     * 短链接不存在时跳转
     * @return
     */
    @RequestMapping("/page/notfound")
    public String notfound() {
        String notfound = "notfound";
        return notfound;
    }

}
