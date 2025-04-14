package com.example.plog.web.controller;

import java.nio.file.attribute.UserPrincipal;
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
import com.example.plog.web.dto.detaillog.PetLogDetailLogDto;
import com.example.plog.web.dto.healthlog.PetLogHealthLogDto;
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

    @PostMapping("api/logs/hospital")
    public ResponseEntity<ApiResponse<PetLogDto>> createHealthLog(
        @RequestBody PetLogHealthLogDto petLogHealthLogDto,
        @CurrentUser UserPrincipal userPrincipal
    ){
        PetLogDto response = petLogService.createHealthLog(petLogHealthLogDto);
        return ApiResponse.success(response);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDetailLogs(
        @PathVariable Long petId,
        @CurrentUser UserPrincipal userPrincipal
    ){
        List<DetailLogDto> detailLogs = petLogService.getDetailLog(petId);
        
        if(detailLogs.isEmpty()){
            return ApiResponse.error("NOT_FOUND", "해당 petId의 로그가 없습니다.", HttpStatus.NOT_FOUND);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("detailLogs", detailLogs);

        return ApiResponse.success(data);
        
    }

    // @GetMapping("/haalth/{petId}")
    // public ResponseEntity<ApiResponse<Map<String, Object>>> getHealthLogs(
    //     @PathVariable Long petId,
    //     @CurrentUser UserPrincipal userPrincipal
    // ){
    //     return
    // }
}
