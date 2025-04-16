package com.example.plog.web.controller;

import com.example.plog.security.UserPrincipal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.config.security.CurrentUser;
import com.example.plog.service.PetLogService;
import com.example.plog.web.dto.ApiResponse;
import com.example.plog.web.dto.detaillog.DetailLogDto;
import com.example.plog.web.dto.detaillog.DetailLogResponseDto;
import com.example.plog.web.dto.detaillog.PetLogDetailLogDto;
import com.example.plog.web.dto.healthlog.HealthLogResponseDto;
import com.example.plog.web.dto.healthlog.PetLogHealthLogDto;
import com.example.plog.web.dto.pet.PetResponseDto;
import com.example.plog.web.dto.petlog.PetLogResponseDto;
import com.example.plog.web.dto.petlog.PetLogDto;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    PetLogService petLogService;

    @PostMapping
    public ResponseEntity<ApiResponse<PetLogDto>> createPetlog(
        @RequestBody PetLogDetailLogDto petLogDetailLogDto,
        @CurrentUser UserPrincipal userPrincipal
    ){
        PetLogDto response = petLogService.createDetailLog(petLogDetailLogDto);
        return ApiResponse.success(response);
    }

    @PostMapping("/health")
    public ResponseEntity<ApiResponse<PetLogDto>> createHealthLog(
        @RequestBody PetLogHealthLogDto petLogHealthLogDto,
        @CurrentUser UserPrincipal userPrincipal
    ){
        PetLogDto response = petLogService.createHealthLog(petLogHealthLogDto);
        return ApiResponse.success(response);
    }

    @GetMapping("/{petName}")
    public ResponseEntity<ApiResponse<List<DetailLogResponseDto>>> getDetailLogs(
        @CurrentUser UserPrincipal userPrincipal,
        @PathVariable String petName
    ){
        List<DetailLogResponseDto> response = petLogService.getDetailLog(userPrincipal, petName);
        return ApiResponse.success(response);
        
    }

    @GetMapping("/health/{petName}")
    public ResponseEntity<ApiResponse<List<HealthLogResponseDto>>> getHealthLogs(
        @PathVariable String petName,
        @CurrentUser UserPrincipal userPrincipal
    ) {
        List<HealthLogResponseDto> response = petLogService.getHealthLog(userPrincipal, petName);
        return ApiResponse.success(response);
    }
}
