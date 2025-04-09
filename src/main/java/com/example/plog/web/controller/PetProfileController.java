package com.example.plog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
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

import lombok.extern.slf4j.Slf4j;



@RestController
// @RequiredArgsConstructor
@RequestMapping("/api/pets")
@Slf4j
public class PetProfileController {

    // private final PetProfileService petProfileService;
    @Autowired
    PetProfileService petProfileService;

    /**
     * @todo login 관련 개발 완료 후, @PostMapping에서 family도 create하게 작업
      */
    @PostMapping
    public ResponseEntity<ApiResponse<PetResponseDto>> createPet(@CurrentUser UserPrincipal userPrincipal, @RequestBody PetProfileDto petProfileDto){
        PetResponseDto response = petProfileService.createPet(userPrincipal,petProfileDto);

        return ApiResponse.success(response);
        
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PetResponseDto>> updatePet(
            @CurrentUser UserPrincipal userPrincipal, 
            @RequestBody PetProfileDto petProfileDto) {
        PetResponseDto response = petProfileService.updatePet(userPrincipal, petProfileDto);
        return ApiResponse.success(response);
    }

        
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deletePet(@PathVariable("id") Long id){
    //     petProfileService.remove(id);
    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

}
