package com.miirso.shortlink.admin.config;

import com.miirso.shortlink.admin.utils.LoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Package com.miirso.shortlink.admin.config
 * @Author miirso
 * @Date 2024/10/11 10:34
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(stringRedisTemplate))
                .excludePathPatterns(
                        "/api/shortlink/admin/v1/user/register",
                        "/api/shortlink/admin/v1/user/login"
                ); // excludePathPatterns, 放行路径
    }
}
