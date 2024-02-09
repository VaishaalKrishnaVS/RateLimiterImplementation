package com.example.greetingserver.exception;

public class RateLimiterReachedException extends RuntimeException{
    public RateLimiterReachedException(String message) {
        super(message);
    }
}
