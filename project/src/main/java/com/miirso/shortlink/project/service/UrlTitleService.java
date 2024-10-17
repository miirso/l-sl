package com.miirso.shortlink.project.service;

import java.io.IOException;

/**
 * @Package com.miirso.shortlink.project.service
 * @Author miirso
 * @Date 2024/10/17 20:15
 */
public interface UrlTitleService {

    /**
     * 根据url获取url的title
     * @param url
     * @return String
     */
    String getUrlTitle(String url) throws IOException;
}
