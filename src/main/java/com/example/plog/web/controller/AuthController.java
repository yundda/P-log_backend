package com.example.plog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.service.AuthService;
import com.example.plog.web.dto.ApiResponse;
import com.example.plog.web.dto.user.UserLoginDto;
import com.example.plog.web.dto.user.UserRegistrationDto;
import com.example.plog.web.dto.user.UserResponseDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        UserResponseDto response = (authService.registerUser(userRegistrationDto));
        return ApiResponse.success(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> loginUser(@RequestBody UserLoginDto userRegistrationDto) {
        UserResponseDto response = authService.createToken(userRegistrationDto);
        return ApiResponse.success(response);
    }
    @GetMapping("/requestInfo{requestId}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getRequestInfo(@PathVariable Long requestId) {
        UserResponseDto response = authService.getRequestInfo(requestId);
        return ApiResponse.success(response);
    }

}
