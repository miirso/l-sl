package com.miirso.shortlink.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Package com.miirso.shortlink.project
 * @Author miirso
 * @Date 2024/10/13 15:54
 */
@SpringBootApplication
@MapperScan("com.miirso.shortlink.project.dao.mapper")
public class ShortLinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShortLinkApplication.class);
    }
}
