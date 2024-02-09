package com.example.greetingserver.service.impl;


import com.example.greetingserver.exception.RateLimiterReachedException;
import com.example.greetingserver.service.RateLimiterFetchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class RateLimiterFetchServiceImpl implements RateLimiterFetchService {
    private final RateLimiterServiceImpl rateLimiterService;

    @Override
    public void requestProcessEligibilityCheck() {

        if (rateLimiterService.resolveBucket().tryConsume(1)) {
            log.info("Tenant Rate Limit is not exceeded");
        } else {
            log.error("Rate Limit Reached");
            throw new RateLimiterReachedException("Limit reached");
        }
    }

}
