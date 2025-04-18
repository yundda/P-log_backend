package com.example.plog.web.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.config.security.CurrentUser;
import com.example.plog.security.UserPrincipal;
import com.example.plog.service.PetLogService;
import com.example.plog.web.dto.ApiResponse;
import com.example.plog.web.dto.detaillog.DetailLogResponseDto;
import com.example.plog.web.dto.detaillog.PetLogDetailLogDto;
import com.example.plog.web.dto.detaillog.PetLogDetailLogPatchDto;
import com.example.plog.web.dto.healthlog.HealthLogPatchDto;
import com.example.plog.web.dto.healthlog.HealthLogResponseDto;
import com.example.plog.web.dto.healthlog.PetLogHealthLogDto;
import com.example.plog.web.dto.petlog.PetLogDto;

import jakarta.ws.rs.BadRequestException;

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

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse<Void>> patchDetailLogs(
        @CurrentUser UserPrincipal userPrincipal,
        @RequestBody PetLogDetailLogPatchDto petlDetailLogPatchDto
    ){
        petLogService.patchDetailLogs(userPrincipal, petlDetailLogPatchDto);
        return ApiResponse.success();
    }

    @PatchMapping("/health/update")
    public ResponseEntity<ApiResponse<Void>> patchHealthLogs(
        @CurrentUser UserPrincipal userPrincipal,
        @RequestBody HealthLogPatchDto healthLogPatchDto
    ){
        petLogService.patchHealthLogs(userPrincipal, healthLogPatchDto);
        return ApiResponse.success();
    }

    @DeleteMapping("/{petName}")
    public ResponseEntity<ApiResponse<Void>> deleteDetailLogs(
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable String petName,
            @RequestParam("time") String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime logTime;
        try {
            logTime = LocalDateTime.parse(timeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("time 파라미터는 yyyyMMddHHmm 형식이어야 합니다.");
        }
        petLogService.deleteDetailLogs(userPrincipal, petName, logTime);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/health/{petName}")
    public ResponseEntity<ApiResponse<Void>> deleteHealthLogs(
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable String petName,
            @RequestParam("time") String timeStr) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime logTime;
        try {
            logTime = LocalDateTime.parse(timeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("time 파라미터는 yyyyMMddHHmmss 형식이어야 합니다.");
        }
        petLogService.deleteHealthLogs(userPrincipal, petName, logTime);
        return ApiResponse.success(null);
    }
}