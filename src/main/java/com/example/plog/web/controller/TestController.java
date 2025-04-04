package com.example.plog.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.web.dto.ApiResponse;


@RestController
@RequestMapping("/api/auth")
public class TestController {
    @GetMapping("/test")
        public ResponseEntity<ApiResponse<String>> test(){
        return ApiResponse.success("성공");
    }
}
