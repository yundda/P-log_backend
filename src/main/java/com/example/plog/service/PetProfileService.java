package com.example.plog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.plog.repository.Enum.Role;
import com.example.plog.repository.detaillog.DetaillogJpaRepository;
import com.example.plog.repository.family.FamilyEntity;
import com.example.plog.repository.family.FamilyJpaRepository;
import com.example.plog.repository.healthlog.HealthlogJpaRepository;
import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.pet.PetJpaRepository;
import com.example.plog.repository.petlog.PetlogJpaRepository;
import com.example.plog.repository.request.RequestJpaRepository;
import com.example.plog.repository.user.UserEntity;
import com.example.plog.repository.user.UserJpaRepository;
import com.example.plog.security.UserPrincipal;
import com.example.plog.service.mapper.PetProfileMapper;
import com.example.plog.service.resolver.EntityFinder;
import com.example.plog.web.dto.pet.PetCreateDto;
import com.example.plog.web.dto.pet.PetProfileListDto;
import com.example.plog.web.dto.pet.PetResponseDto;
import com.example.plog.web.dto.pet.PetUpdateDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetProfileService{

    @Autowired
    PetJpaRepository petJpaRepository;

    @Autowired
    FamilyJpaRepository familyJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    EntityFinder entityFinder;

    @Autowired
    S3Service s3Service;

    @Autowired
    DetaillogJpaRepository detaillogJpaRepository;

    @Autowired
    HealthlogJpaRepository healthlogJpaRepository;

    @Autowired
    PetlogJpaRepository petlogJpaRepository;

    @Autowired
    RequestJpaRepository requestJpaRepository;
    
    public PetResponseDto createPet(UserPrincipal userPrincipal, PetCreateDto petCreateDto, MultipartFile image) {
        String imageUrl = s3Service.upload(image);

        // UserEntity를 데이터베이스에서 조회
        UserEntity userEntity = entityFinder.getUserById(userPrincipal.getId());

        // PetProfileDto를 PetEntity로 변환하고 저장
        PetEntity petEntity = PetProfileMapper.INSTANCE.petCreateDtoToPetEntity(petCreateDto);
        petEntity.setPetPhoto(imageUrl);
        PetEntity savedPet = petJpaRepository.save(petEntity);

        // FamilyEntity 생성 및 저장
        FamilyEntity familyEntity = FamilyEntity.builder()
            .user(userEntity) 
            .pet(savedPet)
            .role(Role.OWNER)
            .build();
        familyJpaRepository.save(familyEntity);

        // PetResponseDto 생성 및 반환
        return PetResponseDto.builder()
            .petName(petEntity.getPetName())
            .petSpecies(petEntity.getPetSpecies())
            .petBreed(petEntity.getPetBreed())
            .petBirthday(petEntity.getPetBirthday())
            .petGender(petEntity.getPetGender())
            .petWeight(petEntity.getPetWeight())
            .petImageUrl(imageUrl)
            .build();
               
    }

    public List<PetProfileListDto> getPetListByUser(UserPrincipal userPrincipal) {
        List<FamilyEntity> families = familyJpaRepository.findByUserId(userPrincipal.getId());

        List<PetProfileListDto> petList = families.stream().map(family ->{
            PetEntity petEntity = family.getPet();
            return PetProfileListDto.builder()
                    .petId(petEntity.getId())
                    .petName(petEntity.getPetName())
                    .petImageUrl(petEntity.getPetPhoto())
                    .role(family.getRole())
                    .build();
        }).collect(Collectors.toList());

        return petList;
    }
    
    public PetResponseDto getPetProfileByUser(UserPrincipal userPrincipal, String name) {
        // 데이터베이스에서 반려동물 엔티티 조회
        PetEntity petEntity = entityFinder.getPetByPetName(name);
        // PetResponseDto 생성 및 반환
        return PetResponseDto.builder()
            .petName(petEntity.getPetName())
            .petSpecies(petEntity.getPetSpecies())
            .petBreed(petEntity.getPetBreed())
            .petBirthday(petEntity.getPetBirthday())
            .petGender(petEntity.getPetGender())
            .petWeight(petEntity.getPetWeight())
            .petImageUrl(petEntity.getPetPhoto())
            .build();
    }

    // 반려동물 정보를 업데이트하는 메서드
    public PetResponseDto updatePet(UserPrincipal userPrincipal,
                @RequestPart("info") PetUpdateDto petUpdateDto,
                @RequestPart(value = "image", required = false) MultipartFile image) {
  
        // 반려동물 이름으로 데이터베이스에서 반려동물 엔티티 조회
        PetEntity entity = entityFinder.getPetByPetName(petUpdateDto.getPetName());
        // DTO의 각 필드가 null이 아닌 경우 엔티티의 해당 필드를 업데이트
        if (petUpdateDto.getPetName() != null) {
            entity.setPetName(petUpdateDto.getPetName());
        }
        if (petUpdateDto.getPetSpecies() != null) {
            entity.setPetSpecies(petUpdateDto.getPetSpecies());
        }
        if (petUpdateDto.getPetBreed() != null) {
            entity.setPetBreed(petUpdateDto.getPetBreed());
        }
        if (petUpdateDto.getPetBirthday() != null) {
            entity.setPetBirthday(petUpdateDto.getPetBirthday());
        }
        if (petUpdateDto.getPetGender() != null) {
            entity.setPetGender(petUpdateDto.getPetGender());
        }
        if (petUpdateDto.getPetWeight() != null) {
            entity.setPetWeight(petUpdateDto.getPetWeight());
        }
        if (image != null && !image.isEmpty()) {
            String imageUrl = s3Service.upload(image); // ✅ 여기에 진짜 업로드 로직 들어감
            entity.setPetPhoto(imageUrl);
        }
        

        petJpaRepository.save(entity);

        // 업데이트된 엔티티를 기반으로 응답 DTO 생성 및 반환
        return PetResponseDto.builder()
            .petName(entity.getPetName())
            .petSpecies(entity.getPetSpecies())
            .petBreed(entity.getPetBreed())
            .petBirthday(entity.getPetBirthday())
            .petGender(entity.getPetGender())
            .petWeight(entity.getPetWeight())
            .petImageUrl(entity.getPetPhoto())
            .build();
    }

    // 반려동물 정보를 삭제하는 메서드
    @Transactional
    public void deletePet(UserPrincipal userPrincipal, String name) {
        UserEntity userEntity = entityFinder.getUserById(userPrincipal.getId());
        // 주어진 petId가 데이터베이스에 존재하지 않으면 예외 발생
        PetEntity petEntity = entityFinder.findPetOrThrowIfNotFoundOrIfNotOwner(name, userEntity);

        detaillogJpaRepository.deleteByLog_Pet(petEntity);
        healthlogJpaRepository.deleteByLog_Pet(petEntity);
        petlogJpaRepository.deleteByPet(petEntity);
        requestJpaRepository.deleteByPet(petEntity);
        // S3에서 이미지 삭제



        // 데이터베이스에서 관련 FamilyEntity 삭제
        familyJpaRepository.deleteAllByPet(petEntity);

        // 데이터베이스에서 반려동물 엔티티 삭제
        petJpaRepository.deleteById(petEntity.getId());
        
    }
}
