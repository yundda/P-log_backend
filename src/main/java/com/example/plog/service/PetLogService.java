package com.example.plog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plog.repository.Enum.Type;
import com.example.plog.repository.detaillog.DetaillogEntity;
import com.example.plog.repository.detaillog.DetaillogJpaRepository;
import com.example.plog.repository.family.FamilyEntity;
import com.example.plog.repository.family.FamilyJpaRepository;
import com.example.plog.repository.healthlog.HealthlogEntity;
import com.example.plog.repository.healthlog.HealthlogJpaRepository;
import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.pet.PetJpaRepository;
import com.example.plog.repository.petlog.PetlogEntity;
import com.example.plog.repository.petlog.PetlogJpaRepository;
import com.example.plog.repository.user.UserEntity;
import com.example.plog.web.dto.detaillog.DetailLogDto;
import com.example.plog.web.dto.detaillog.PetLogDetailLogDto;
import com.example.plog.web.dto.healthlog.HealthLogDto;
import com.example.plog.web.dto.healthlog.PetLogHealthLogDto;
import com.example.plog.web.dto.petlog.PetLogDto;

import lombok.RequiredArgsConstructor;

/**
 * 반려동물 로그와 관련된 상세 로그를 관리하는 서비스 클래스입니다.
 * 이 클래스는 데이터베이스에 반려동물 로그와 상세 로그를 생성하고 저장하는 메서드를 제공합니다.
 * 
 * 의존성:
 * - PetlogJpaRepository: PetlogEntity를 관리하는 리포지토리.
 * - DetaillogJpaRepository: DetaillogEntity를 관리하는 리포지토리.
 * 
 * 메서드:
 * - createDetailLog(PetLogDetailLogDto dto): 새로운 반려동물 로그를 생성하고,
 *   필요 시 관련된 상세 로그를 생성합니다. 저장된 데이터를 포함하는 PetLogDto를 반환합니다.
 * - convertToPetlogEntity(PetLogDto petLogDto): PetLogDto를 PetlogEntity로 변환합니다.
 * - convertToDetaillogEntity(DetailLogDto detailLogDto): DetailLogDto를 DetaillogEntity로 변환합니다.
 * 
 * 참고:
 * - 반려동물 로그의 타입이 "HOSPITAL"이 아닌 경우, 상세 로그도 생성 및 저장됩니다.
 * - 반환되는 PetLogDto는 반려동물 로그와 (해당되는 경우) 상세 로그의 정보를 포함합니다.
 */
@Service
@RequiredArgsConstructor
public class PetLogService {

    @Autowired
    PetlogJpaRepository petlogJpaRepository;

    @Autowired
    DetaillogJpaRepository detaillogJpaRepository;

    @Autowired
    HealthlogJpaRepository healthlogJpaRepository;

    @Autowired
    FamilyJpaRepository familyJpaRepository;

    @Autowired
    PetJpaRepository petJpaRepository;

    
    public PetLogDto createDetailLog(PetLogDetailLogDto petLogDetailLogDto) {
        String petName = petLogDetailLogDto.getPetlog().getName();
        PetEntity petEntity = petJpaRepository.findByPetName(petName)
                                .orElseThrow(() -> new RuntimeException("해당 이름의 반려동물을 찾을 수 없습니다. name: " + petName));

        FamilyEntity familyEntity = familyJpaRepository.findById(petEntity.getId())
                                .orElseThrow(() -> new RuntimeException("해당 ID의 FamilyEntity를 찾을 수 없습니다. ID: " + petEntity.getId()));

        Long userId = familyEntity.getUser().getId();

        petLogDetailLogDto.getPetlog().setPetId(petEntity.getId());
        petLogDetailLogDto.getPetlog().setUserId(userId);

        PetlogEntity petlogEntity = convertToPetlogEntity(petLogDetailLogDto.getPetlog());
        PetlogEntity savedPetlog = petlogJpaRepository.save(petlogEntity);
        DetaillogEntity detailLogEntity = null;
        
        // 반려동물 로그의 타입이 "HOSPITAL"이 아닌 경우 상세 로그 생성
        if (!savedPetlog.getType().equals(Type.HOSPITAL)) {
            detailLogEntity = convertToDetaillogEntity(petLogDetailLogDto.getDetailLog());
            detailLogEntity.setLog_id(savedPetlog);
            detaillogJpaRepository.save(detailLogEntity);
        }

        // 생성된 반려동물 로그와 상세 로그 정보를 PetLogDto로 반환
        return PetLogDto.builder()
                .petId(petlogEntity.getPet_id().getId())
                .userId(petlogEntity.getUser_id().getId())
                .type(petlogEntity.getType())
                .logId(detailLogEntity != null ? detailLogEntity.getLog_id().getId() : null)
                .logTime(detailLogEntity != null ? detailLogEntity.getLog_time() : null)
                .mealType(detailLogEntity != null ? detailLogEntity.getMeal_type() : null)
                .place(detailLogEntity != null ? detailLogEntity.getPlace() : null)
                .price(detailLogEntity != null ? detailLogEntity.getPrice() : null)
                .takeTime(detailLogEntity != null ? detailLogEntity.getTake_time() : null)
                .memo(detailLogEntity != null ? detailLogEntity.getMemo() : null)
                .build();
    }

    private PetlogEntity convertToPetlogEntity(PetLogDto petLogDto) {
        return PetlogEntity.builder()
                .pet_id(PetEntity.builder().id(petLogDto.getPetId()).build())      
                .user_id(UserEntity.builder().id(petLogDto.getUserId()).build())
                .type(petLogDto.getType())
                .build();
    }

    private DetaillogEntity convertToDetaillogEntity(DetailLogDto detailLogDto) {
        return DetaillogEntity.builder()
                .log_time(detailLogDto.getLogTime())
                .meal_type(detailLogDto.getMealtype())
                .place(detailLogDto.getPlace())
                .price(detailLogDto.getPrice())
                .take_time(detailLogDto.getTakeTime())
                .memo(detailLogDto.getMemo())
                .build();
    }


    /**
     * 새로운 반려동물 건강 로그를 생성하고, 필요 시 관련된 건강 로그를 생성합니다.
     * 저장된 데이터를 포함하는 PetLogDto를 반환합니다.
     *
     * @param dto 반려동물 로그와 건강 로그 정보를 포함하는 DTO
     * @return 생성된 반려동물 로그와 건강 로그 정보를 포함하는 PetLogDto
     */
    public PetLogDto createHealthLog(PetLogHealthLogDto dto) {
        // PetLogDto를 PetlogEntity로 변환 후 저장
        PetlogEntity petlogEntity = convertToPetlogEntity(dto.getPetlog());
        PetlogEntity savedPetlog = petlogJpaRepository.save(petlogEntity);
        HealthlogEntity healthlogEntity = null;

        // 반려동물 로그의 타입이 "HOSPITAL"인 경우 건강 로그 생성
        if (savedPetlog.getType().equals(Type.HOSPITAL)) {
            healthlogEntity = convertToHealthLogEntity(dto.getHealthLog());
            healthlogEntity.setLog_id(savedPetlog);
            healthlogJpaRepository.save(healthlogEntity);
        }

        // 생성된 반려동물 로그와 건강 로그 정보를 PetLogDto로 반환
        return PetLogDto.builder()
        .petId(petlogEntity.getPet_id().getId())
        .userId(petlogEntity.getUser_id().getId())
        .type(petlogEntity.getType())
        .logId(healthlogEntity != null ? healthlogEntity.getLog_id().getId() : null)
        .logTime(healthlogEntity != null ? healthlogEntity.getHospital_log() : null)
        .build();
    }


    

    /**
     * HealthLogDto를 HealthlogEntity로 변환합니다.
     *
     * @param healthLogDto 변환할 HealthLogDto 객체
     * @return 변환된 HealthlogEntity 객체
     */
    private HealthlogEntity convertToHealthLogEntity(HealthLogDto healthLogDto) {
        return HealthlogEntity.builder()
                .vaccination(healthLogDto.getVaccination()) 
                .vaccination_log(healthLogDto.getVaccinationLog())
                .hospital(healthLogDto.getHospital()) 
                .hospital_log(healthLogDto.getHospitalLog())
                .build();
    }
    
    public List<DetailLogDto> getDetailLog(Long petId){
        List<DetaillogEntity> detaillogEntities = detaillogJpaRepository.findAllByPetId(petId);

        return detaillogEntities.stream()
                .map(this::convertToDetailLogDto)
                .collect(Collectors.toList());
    }

    public DetailLogDto convertToDetailLogDto(DetaillogEntity entity){
        if(entity == null) return null;
        return DetailLogDto.builder()
                .logTime(entity.getLog_time())
                .mealtype(entity.getMeal_type())
                .place(entity.getPlace())
                .takeTime(entity.getTake_time())
                .memo(entity.getMemo())
                .build();
    }

    // private List<HealthLogDto> getHealthLog(Long petId){
    //     List<HealthlogEntity> healthlogEntities = healthlogJpaRepository.findAllByPetId(petId);

    //     return healthlogEntities.stream()
    //             .map(this::convertToHealthLogDto)
    //             .collect(Collectors.toList());
    // }

    private HealthLogDto convertHealthLogDto(HealthlogEntity entity){
        if(entity == null) return null;
        return HealthLogDto.builder()
        .vaccination(entity.getVaccination())
        .vaccinationLog(entity.getVaccination_log())
        .hospital(entity.getHospital())
        .hospitalLog(entity.getHospital_log())
                .build();
    }
}
