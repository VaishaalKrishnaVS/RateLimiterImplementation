package com.example.greetingserver.controller;

import com.example.greetingserver.dto.ResponseDto;
import com.example.greetingserver.service.RateLimiterFetchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    private RateLimiterFetchService rateLimiterFetchService;

    @GetMapping("/hello")
    public ResponseEntity<ResponseDto> createUser(){
        rateLimiterFetchService.requestProcessEligibilityCheck();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto("201","Hello"));

    }

}
