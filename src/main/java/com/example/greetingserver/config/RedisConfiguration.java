package com.example.greetingserver.config;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;


@Configuration
public class RedisConfiguration {

    private static final String RATE_LIMITER_CACHE = "Rate Limiter";

    @Bean
    public Config config()
    {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379");
        return config;
    }

    @Bean
    public CacheManager cacheManager(Config config) {
        CacheManager manager = Caching.getCachingProvider().getCacheManager();
        manager.createCache(RATE_LIMITER_CACHE, RedissonConfiguration.fromConfig(config));
        return manager;
    }

    @Bean
    ProxyManager<String> proxyManager(CacheManager cacheManager) {
        return new JCacheProxyManager<>(cacheManager.getCache(RATE_LIMITER_CACHE));
    }

    @Bean
    public RedissonClient redissonClient(){
        return Redisson.create(config());
    }

}
