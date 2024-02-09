package com.example.greetingserver.service;

import io.github.bucket4j.Bucket;

public interface RateLimiterService {
    Bucket resolveBucket();

}
