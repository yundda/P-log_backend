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
import com.example.plog.web.dto.pet.PetProfileListDto;
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
    
    public PetResponseDto createPet(UserPrincipal userPrincipal, PetProfileDto petProfileDto) {
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

    public List<PetProfileListDto> getPetListByUser(UserPrincipal userPrincipal) {
        List<FamilyEntity> families = familyJpaRepository.findByUserId(userPrincipal.getId());

        List<PetProfileListDto> petList = families.stream().map(family ->{
            PetEntity petEntity = family.getPet();
            return PetProfileListDto.builder()
                    .petId(petEntity.getId())
                    .petName(petEntity.getName())
                    .petImageUrl(petEntity.getPhoto())
                    .role(family.getRole())
                    .build();
        }).collect(Collectors.toList());

        return petList;
    }
    
    public PetResponseDto getPetById(Long petId) {
        // 데이터베이스에서 반려동물 엔티티 조회
        PetEntity petEntity = petJpaRepository.findById(petId)
            .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + petId));

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
    public PetResponseDto updatePet(UserPrincipal userPrincipal, Long petId, PetProfileDto petProfileDto) {
        Long userId = userPrincipal.getId(); // 현재 사용자의 ID를 가져옴
        petProfileDto.setOwnerId(userId); // 반려동물 프로필 DTO에 소유자 ID 설정

        // 반려동물 이름으로 데이터베이스에서 반려동물 엔티티 조회
        PetEntity entity = petJpaRepository.findById(petId)
            .orElseThrow(() -> new RuntimeException("Pet not found with petId: " +petId));

        // DTO의 각 필드가 null이 아닌 경우 엔티티의 해당 필드를 업데이트
        if (petProfileDto.getPetName() != null) {
            entity.setName(petProfileDto.getPetName());
        }
        if (petProfileDto.getPetSpecies() != null) {
            entity.setSpecies(petProfileDto.getPetSpecies());
        }
        if (petProfileDto.getPetBreed() != null) {
            entity.setBreed(petProfileDto.getPetBreed());
        }
        if (petProfileDto.getPetBirthday() != null) {
            entity.setBirthday(petProfileDto.getPetBirthday());
        }
        if (petProfileDto.getPetGender() != null) {
            entity.setGender(petProfileDto.getPetGender());
        }
        if (petProfileDto.getPetWeight() != null) {
            entity.setWeight(petProfileDto.getPetWeight());
        }
        if (petProfileDto.getPetPhoto() != null) {
            entity.setPhoto(petProfileDto.getPetPhoto());
        }

        petJpaRepository.save(entity);

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
        // 수정 필요
        // familyJpaRepository.deleteByPetId(petId);
        petJpaRepository.deleteById(petId);
        
    }
}
