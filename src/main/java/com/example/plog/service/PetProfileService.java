package com.example.plog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plog.repository.Enum.Role;
import com.example.plog.repository.family.FamilyEntity;
import com.example.plog.repository.family.FamilyJpaRepository;
import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.pet.PetJpaRepository;
import com.example.plog.repository.user.UserEntity;
import com.example.plog.repository.user.UserJpaRepository;
import com.example.plog.security.UserPrincipal;
import com.example.plog.service.exceptions.DatabaseException;
import com.example.plog.service.mapper.PetProfileMapper;
import com.example.plog.web.dto.PetProfileDto;
import com.example.plog.web.dto.pet.PetResponseDto;
import com.example.plog.web.dto.user.UserRegistrationDto;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetProfileService{

    @Autowired
    PetJpaRepository petJpaRepository;

    @Autowired
    FamilyJpaRepository familyJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;
    
    public PetResponseDto createPet(UserPrincipal userPrincipal, PetProfileDto petProfileDto, UserRegistrationDto userRegistrationDto) {
        Long userId = userPrincipal.getId();
        petProfileDto.setOwnerId(userId);

        // PetProfileDto를 PetEntity로 변환하고 저장
        PetEntity petEntity = PetProfileMapper.INSTANCE.petProfileDtoToPetEntity(petProfileDto);
        petJpaRepository.save(petEntity);

        // UserEntity를 데이터베이스에서 조회
        UserEntity userEntity = userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // FamilyEntity 생성 및 저장
        FamilyEntity familyEntity = FamilyEntity.builder()
            .user(userEntity) 
            .pet(petEntity)
            .role(Role.OWNER)
            .build();

        familyJpaRepository.save(familyEntity);

        // PetResponseDto 생성 및 반환
        return PetResponseDto.builder()
            .petId(petEntity.getId())
            .petName(petEntity.getName())
            .petSpecies(petEntity.getSpecies())
            .petBreed(petEntity.getBreed())
            .petBirthday(petEntity.getBirthday())
            .petGender(petEntity.getGender())
            .petWeight(petEntity.getWeight())
            .petImageUrl(petEntity.getPhoto())
            .build();
               
    }

    
    
    // 반려동물 정보를 업데이트하는 메서드
    public PetResponseDto updatePet(UserPrincipal userPrincipal, PetProfileDto petProfileDto, UserRegistrationDto userRegistrationDto) {
        Long userId = userPrincipal.getId(); // 현재 사용자의 ID를 가져옴
        petProfileDto.setOwnerId(userId); // 반려동물 프로필 DTO에 소유자 ID 설정

        // 반려동물 이름으로 데이터베이스에서 반려동물 엔티티 조회
        PetEntity entity = petJpaRepository.findByName(petProfileDto.getName())
            .orElseThrow(() -> new RuntimeException("Pet not found with name: " + petProfileDto.getName()));

        // DTO의 각 필드가 null이 아닌 경우 엔티티의 해당 필드를 업데이트
        if (petProfileDto.getName() != null) {
            entity.setName(petProfileDto.getName());
        }
        if (petProfileDto.getSpecies() != null) {
            entity.setSpecies(petProfileDto.getSpecies());
        }
        if (petProfileDto.getBreed() != null) {
            entity.setBreed(petProfileDto.getBreed());
        }
        if (petProfileDto.getBirthday() != null) {
            entity.setBirthday(petProfileDto.getBirthday());
        }
        if (petProfileDto.getGender() != null) {
            entity.setGender(petProfileDto.getGender());
        }
        if (petProfileDto.getWeight() != null) {
            entity.setWeight(petProfileDto.getWeight());
        }
        if (petProfileDto.getPhoto() != null) {
            entity.setPhoto(petProfileDto.getPhoto());
        }

        // 업데이트된 엔티티를 기반으로 응답 DTO 생성 및 반환
        return PetResponseDto.builder()
            .petId(entity.getId())
            .petName(entity.getName())
            .petSpecies(entity.getSpecies())
            .petBreed(entity.getBreed())
            .petBirthday(entity.getBirthday())
            .petGender(entity.getGender())
            .petWeight(entity.getWeight())
            .petImageUrl(entity.getPhoto())
            .build();
    }

    // 반려동물 정보를 삭제하는 메서드
    public void deletePet(Long petId) {
        // 주어진 petId가 데이터베이스에 존재하지 않으면 예외 발생
        if (!petJpaRepository.existsById(petId)) {
            throw new DatabaseException("해당 petId의 데이터가 존재하지 않습니다.");
        }

        // 데이터베이스에서 반려동물 엔티티 삭제
        petJpaRepository.deleteById(petId);
    }
}
