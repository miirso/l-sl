package com.miirso.shortlink.project.service.impl;

import com.miirso.shortlink.project.service.UrlTitleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Package com.miirso.shortlink.project.service.impl
 * @Author miirso
 * @Date 2024/10/17 20:15
 */

@Service
public class UrlTitleServiceImpl implements UrlTitleService {

    @Override
    public String getUrlTitle(String url) throws IOException {
        URL tagetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) tagetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Document document = Jsoup.connect(url).get();
            return document.title();
        }

        return "Error while fetching title";
    }
}
