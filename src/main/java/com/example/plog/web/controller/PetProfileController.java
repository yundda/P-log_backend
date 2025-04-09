package com.example.plog.web.controller;

import java.util.HashMap;
import java.util.Map;

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
        // Map<String, Object> response = new HashMap<>();
        // response.put("message", "반려동물이 성공적으로 등록되었습니다.");
        // response.put("pet", pet);

        return ApiResponse.success(response);
        // return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<PetProfileDto> updatePet(@RequestBody PetProfileDto petProfileDto){
    //     petProfileService.modify(petProfileDto);
    //     return new ResponseEntity<>(petProfileDto, HttpStatus.OK);
    // }
    
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deletePet(@PathVariable("id") Long id){
    //     petProfileService.remove(id);
    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

}
