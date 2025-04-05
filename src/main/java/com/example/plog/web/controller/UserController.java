package com.example.plog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.config.security.CurrentUser;
import com.example.plog.security.JwtAuthenticationFilter;
import com.example.plog.security.TokenProvider;
import com.example.plog.security.UserPrincipal;
import com.example.plog.service.UserService;
import com.example.plog.web.dto.ApiResponse;
import com.example.plog.web.dto.user.UserResponseDto;
import com.example.plog.web.dto.user.UserUpdateDto;


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
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserInfo(@CurrentUser UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        UserResponseDto response = userService.getUserInfo(userId);
        return ApiResponse.success(response);
    }
    @PatchMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateUser(
                @CurrentUser UserPrincipal userPrincipal, @RequestBody UserUpdateDto updateInfo) {
        Long userId = userPrincipal.getId();
        userService.updateUser(userId, updateInfo);
        return ApiResponse.success();
    }

}
