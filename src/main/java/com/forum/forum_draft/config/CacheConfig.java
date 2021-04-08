package com.forum.forum_draft.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

//    @Bean
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager("user","message","comment");
//        cacheManager.setCaffeine(cacheProperties());
//        return cacheManager;
//    }
//
//    public Caffeine<Object, Object> cacheProperties() {
//        return Caffeine.newBuilder()
//                .initialCapacity(100)
//                .maximumSize(500)
//                .expireAfterAccess(10, TimeUnit.MINUTES)
//                .weakKeys()
//                .recordStats();
//    }
}
