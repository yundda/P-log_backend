package com.example.plog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.config.security.CurrentUser;
import com.example.plog.security.UserPrincipal;
import com.example.plog.service.RequestService;
import com.example.plog.web.dto.ApiResponse;
import com.example.plog.web.dto.request.RequestInviteDto;
import com.example.plog.web.dto.request.RequestPermissionDto;
import com.example.plog.web.dto.user.UserResponseDto;


@RestController
@RequestMapping("/api/request")
public class RequestController {
    @Autowired
    RequestService requestService;
    
    @PostMapping("/permission")
    public ResponseEntity<ApiResponse<UserResponseDto>> requestPermission(@CurrentUser UserPrincipal userPrincipal, @RequestBody RequestPermissionDto requestPermissionDto) {
        UserResponseDto response = requestService.requestPermission(userPrincipal, requestPermissionDto);
        return ApiResponse.success(response);
    }

    @PostMapping("/invite")
    public ResponseEntity<ApiResponse<UserResponseDto>> requestInvite(@CurrentUser UserPrincipal userPrincipal, @RequestBody RequestInviteDto requestInviteDto) {
        UserResponseDto response = requestService.requestInvite(userPrincipal, requestInviteDto);
        return ApiResponse.success(response);
    }

    @GetMapping("/pending/{requestId}")
    public ResponseEntity<ApiResponse<UserResponseDto>> requestPending(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long requestId) {
        UserResponseDto response = requestService.requestPending(userPrincipal, requestId );
        return ApiResponse.success(response);
    }

    @GetMapping("/accept/{requestId}")
    public ResponseEntity<ApiResponse<Void>> requestAccept(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long requestId) {
        requestService.requestAccept(userPrincipal, requestId);
        return ApiResponse.success();
    }
    @GetMapping("/reject/{requestId}")
    public ResponseEntity<ApiResponse<Void>> requestReject(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long requestId) {
        requestService.requestReject(userPrincipal, requestId);
        return ApiResponse.success();
    }
}
