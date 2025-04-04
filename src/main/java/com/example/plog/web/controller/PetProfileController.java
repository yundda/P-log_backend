package com.example.plog.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plog.service.PetProfileService;
import com.example.plog.web.dto.PetProfileDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
@Slf4j
public class PetProfileController {

    private final PetProfileService petProfileService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPet(@RequestBody PetProfileDto PetProfileDto){
        Long id = petProfileService.register(PetProfileDto);
        PetProfileDto pet = petProfileService.read(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "반려동물이 성공적으로 등록되었습니다.");
        response.put("pet", pet);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePet(@RequestBody PetProfileDto PetProfileDto){
        petProfileService.modify(PetProfileDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable("id") Long id){
        petProfileService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
