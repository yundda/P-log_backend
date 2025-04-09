package com.example.plog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.example.plog.web.dto.PetProfileDto;
import com.example.plog.web.dto.pet.PetResponseDto;
import com.example.plog.web.dto.user.UserRegistrationDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




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
              @RequestBody PetProfileDto petProfileDto,
              @RequestBody UserRegistrationDto userRegistrationDto) {
          PetResponseDto response = petProfileService.createPet(userPrincipal, petProfileDto, userRegistrationDto);
          return ApiResponse.success(response);
      }

      @GetMapping
      public ResponseEntity<ApiResponse<PetResponseDto>> getPetsByUser(UserPrincipal userPrincipal) {
            PetResponseDto response = petProfileService.getPetsByUser(userPrincipal);
            return ApiResponse.success(response);
      }
      
      @PatchMapping("/{id}")
      public ResponseEntity<ApiResponse<PetResponseDto>> updatePet(
              @CurrentUser UserPrincipal userPrincipal, 
              @RequestBody PetProfileDto petProfileDto,
              @RequestBody UserRegistrationDto userRegistrationDto) {
          PetResponseDto response = petProfileService.updatePet(userPrincipal, petProfileDto, userRegistrationDto);
          return ApiResponse.success(response);
      }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePet(@PathVariable("id") Long petId){
        petProfileService.deletePet(petId);
        return ApiResponse.success();
    }

}
