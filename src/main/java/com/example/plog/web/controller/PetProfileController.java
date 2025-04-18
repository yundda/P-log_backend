package com.example.plog.web.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.config.security.CurrentUser;
import com.example.plog.security.UserPrincipal;
import com.example.plog.service.PetProfileService;
import com.example.plog.web.dto.ApiResponse;
import com.example.plog.web.dto.pet.PetCreateDto;
import com.example.plog.web.dto.pet.PetProfileListDto;
import com.example.plog.web.dto.pet.PetResponseDto;
import com.example.plog.web.dto.pet.PetUpdateDto;

import lombok.extern.slf4j.Slf4j;




@RestController
// @RequiredArgsConstructor
@RequestMapping("/api/pets")
@Slf4j
public class PetProfileController {

    @Autowired
    PetProfileService petProfileService;

      @PostMapping
      public ResponseEntity<ApiResponse<PetResponseDto>> createPet(
              @CurrentUser UserPrincipal userPrincipal, 
              @RequestBody PetCreateDto petCreateDto) {
          PetResponseDto response = petProfileService.createPet(userPrincipal, petCreateDto);
          return ApiResponse.success(response);
      }

      @GetMapping
      public ResponseEntity<ApiResponse<List<PetProfileListDto>>> getPetListByUser(@CurrentUser UserPrincipal userPrincipal) {
        List<PetProfileListDto> petList = petProfileService.getPetListByUser(userPrincipal);
        return ApiResponse.success(petList);
      }

      @GetMapping("/profile/{petName}")
      public ResponseEntity<ApiResponse<PetResponseDto>> getPetById(
        @CurrentUser UserPrincipal userPrincipal,
        @PathVariable String petName
        ) {
        PetResponseDto response = petProfileService.getPetProfileByUser(userPrincipal, petName);
        return ApiResponse.success(response);
    }

      @PatchMapping("/update")
      public ResponseEntity<ApiResponse<PetResponseDto>> updatePet(
              @CurrentUser UserPrincipal userPrincipal, 
              @RequestBody PetUpdateDto petUpdateDto) {
        PetResponseDto response = petProfileService.updatePet(userPrincipal, petUpdateDto);
        return ApiResponse.success(response);
      }
 
      @DeleteMapping("/delete/{petName}")
      public ResponseEntity<ApiResponse<Void>> deletePet(
        @CurrentUser UserPrincipal userPrincipal,
        @PathVariable String petName
        ){
          petProfileService.deletePet(userPrincipal, petName);
          return ApiResponse.success();
      }

}
