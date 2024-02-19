package com.example.greetingserver.filter;

import com.example.greetingserver.exception.RateLimiterReachedException;
import com.example.greetingserver.service.RateLimiterFetchService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private RateLimiterFetchService rateLimiterFetchService;

    public RateLimitFilter(RateLimiterFetchService rateLimiterFetchService) {
        this.rateLimiterFetchService = rateLimiterFetchService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws RateLimiterReachedException, ServletException, IOException {
        try {
            rateLimiterFetchService.requestProcessEligibilityCheck();
            filterChain.doFilter(request, response);
        }
        catch (RateLimiterReachedException e){
            response.sendError(429, e.getMessage());
        }
    }
}
