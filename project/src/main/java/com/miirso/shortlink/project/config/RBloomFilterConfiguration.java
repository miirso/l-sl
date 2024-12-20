package com.miirso.shortlink.project.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RBloomFilterConfiguration {

    /**
     * 防止短链接创建时查询数据库的过滤器
     */
    @Bean
    public RBloomFilter<String> shortUriCreteCachePenetrationBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter("xxx");
        cachePenetrationBloomFilter.tryInit(100000000L, 0.001);
        return cachePenetrationBloomFilter;
    }
}
