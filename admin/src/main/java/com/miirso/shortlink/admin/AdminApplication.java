package com.miirso.shortlink.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Package com.miirso.shortlink.admin
 * @Author miirso
 * @Date 2024/10/10 19:36
 */
@SpringBootApplication
@MapperScan("com.miirso.shortlink.admin.dao.mapper") // 具体到mapper路径，减少不必要的扫描，性能提升一点点...
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
