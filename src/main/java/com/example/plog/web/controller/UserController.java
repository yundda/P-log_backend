package com.example.plog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.security.JwtAuthenticationFilter;
import com.example.plog.security.TokenProvider;
import com.example.plog.service.UserService;
import com.example.plog.web.dto.ApiResponse;
import com.example.plog.web.dto.UserResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    


    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserInfo(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getPrincipal().toString());
        UserResponseDto response = userService.getUserInfo(userId);
        return ApiResponse.success(response);
    }

}
