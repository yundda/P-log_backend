package com.example.plog.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.service.AuthService;
import com.example.plog.web.dto.ApiResponse;
import com.example.plog.web.dto.UserInfoDto;
import com.example.plog.web.dto.UserLoginDto;
import com.example.plog.web.dto.UserRegistrationDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserInfoDto>> registerUser(UserRegistrationDto userRegistrationDto) {
        UserInfoDto userInfoDto = authService.registerUser(userRegistrationDto);
        return ApiResponse.success(userInfoDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(UserLoginDto userRegistrationDto) {
        String token = authService.createToken(userRegistrationDto);
        return ApiResponse.success(token);
    }

}
