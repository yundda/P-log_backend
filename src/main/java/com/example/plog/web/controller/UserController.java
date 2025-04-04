package com.example.plog.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    // @PostMapping("/login")
    // public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {
    //     String token = userService.login(loginRequest);
    //     return ApiResponse.success(token);
    // }

    // @GetMapping("/me")
    // public ResponseEntity<ApiResponse<UserResponse>> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    //     UserResponse userResponse = userService.getUserInfo(userDetails.getUsername());
    //     return ApiResponse.success(userResponse);
    // }
    
}
