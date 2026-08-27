package com.financetracker.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisCacheConfig {
    // Enables Spring Cache Abstraction (@Cacheable, @CacheEvict, @CachePut)
}
