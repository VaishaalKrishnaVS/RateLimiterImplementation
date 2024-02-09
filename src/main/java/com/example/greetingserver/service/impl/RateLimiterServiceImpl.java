package com.example.greetingserver.service.impl;

import com.example.greetingserver.service.RateLimiterService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {
    private final ProxyManager buckets;
    @Value("${ratelimiter.duration}")
    int duration;

    @Value("${ratelimiter.defaultLimit}")
    int capacity;

    public RateLimiterServiceImpl(ProxyManager buckets) {
        this.buckets = buckets;
    }

    public Bucket resolveBucket() {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForTenant(capacity);
        return buckets.builder().build("bucket",configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForTenant(int capacity) {
        Refill refill = Refill.intervally(capacity, Duration.ofMinutes(duration));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}
