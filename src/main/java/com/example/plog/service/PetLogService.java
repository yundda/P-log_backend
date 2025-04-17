package com.example.plog.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
import com.example.plog.security.UserPrincipal;
import com.example.plog.web.dto.detaillog.DetailLogDto;
import com.example.plog.web.dto.detaillog.DetailLogResponseDto;
import com.example.plog.web.dto.detaillog.PetLogDetailLogDto;
import com.example.plog.web.dto.detaillog.PetLogDetailLogPatchDto;
import com.example.plog.web.dto.healthlog.HealthLogDto;
import com.example.plog.web.dto.healthlog.HealthLogPatchDto;
import com.example.plog.web.dto.healthlog.HealthLogResponseDto;
import com.example.plog.web.dto.healthlog.PetLogHealthLogDto;
import com.example.plog.web.dto.petlog.PetLogDto;
import com.example.plog.web.dto.petlog.PetLogDtoForHealth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


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

    
    // DetailLog 생성 메서드
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
        
        // PetLog의 타입이 "HOSPITAL"이 아닌 경우 상세 로그 생성
        if (!savedPetlog.getType().equals(Type.HOSPITAL)) {
            detailLogEntity = convertToDetaillogEntity(petLogDetailLogDto.getDetailLog());
            detailLogEntity.setLog_id(savedPetlog);
            detaillogJpaRepository.save(detailLogEntity);
        }

        // 생성된 PetLog와 DetailLog 정보를 PetLogDto로 반환
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

    // PetLogDto를 PetlogEntity로 변환하는 메서드
    private PetlogEntity convertToPetlogEntity(PetLogDto petLogDto) {
        return PetlogEntity.builder()
                .pet_id(PetEntity.builder().id(petLogDto.getPetId()).build())      
                .user_id(UserEntity.builder().id(petLogDto.getUserId()).build())
                .type(petLogDto.getType())
                .build();
    }

    // DetailLogDto를 DetaillogEntity로 변환하는 메서드
    private DetaillogEntity convertToDetaillogEntity(DetailLogDto detailLogDto) {
        return DetaillogEntity.builder()
                .log_time(detailLogDto.getLogTime())
                .meal_type(detailLogDto.getMealType())
                .place(detailLogDto.getPlace())
                .price(detailLogDto.getPrice())
                .take_time(detailLogDto.getTakeTime())
                .memo(detailLogDto.getMemo())
                .build();
    }

    // HealthLog 생성 메서드
    public PetLogDto createHealthLog(PetLogHealthLogDto petLogHealthLogDto) {
        String petName = petLogHealthLogDto.getPetlog().getName();
        PetEntity petEntity = petJpaRepository.findByPetName(petName)
                                .orElseThrow(() -> new RuntimeException("해당 이름의 반려동물을 찾을 수 없습니다. name: " + petName));

        FamilyEntity familyEntity = familyJpaRepository.findById(petEntity.getId())
                                .orElseThrow(() -> new RuntimeException("해당 ID의 FamilyEntity를 찾을 수 없습니다. ID: " + petEntity.getId()));

        Long userId = familyEntity.getUser().getId();

        petLogHealthLogDto.getPetlog().setPetId(petEntity.getId());
        petLogHealthLogDto.getPetlog().setUserId(userId);

        // PetLogDto를 PetlogEntity로 변환 후 저장
        PetlogEntity petlogEntity = convertToPetlogEntity(petLogHealthLogDto.getPetlog());
        PetlogEntity savedPetlog = petlogJpaRepository.save(petlogEntity);
        HealthlogEntity healthlogEntity = null;

        // PetLog의 타입이 "HOSPITAL"인 경우 건강 로그 생성
        if (savedPetlog.getType().equals(Type.HOSPITAL)) {
            healthlogEntity = convertToHealthLogEntity(petLogHealthLogDto.getHealthLog());
            healthlogEntity.setLog_id(savedPetlog);
            healthlogJpaRepository.save(healthlogEntity);
        }

        // 생성된 PetLog와 HealthLog 정보를 PetLogDto로 반환
        return PetLogDto.builder()
        .petId(petlogEntity.getPet_id().getId())
        .userId(petlogEntity.getUser_id().getId())
        .type(petlogEntity.getType())
        .logId(healthlogEntity != null ? healthlogEntity.getLog_id().getId() : null)
        .logDate(healthlogEntity != null ? healthlogEntity.getHospital_log() : null)
        .build();
    }

    // PetLogDtoForHealth를 PetlogEntity로 변환하는 메서드
    private PetlogEntity convertToPetlogEntity(PetLogDtoForHealth petLogDtoForHealth) {
        return PetlogEntity.builder()
                .pet_id(PetEntity.builder().id(petLogDtoForHealth.getPetId()).build())      
                .user_id(UserEntity.builder().id(petLogDtoForHealth.getUserId()).build())
                .type(petLogDtoForHealth.getType())
                .build();
    }

    // HealthLogDto를 HealthlogEntity로 변환하는 메서드
    private HealthlogEntity convertToHealthLogEntity(HealthLogDto healthLogDto) {
        return HealthlogEntity.builder()
                .vaccination(healthLogDto.getVaccination()) 
                .vaccination_log(healthLogDto.getVaccinationLog())
                .hospital(healthLogDto.getHospital()) 
                .hospital_log(healthLogDto.getHospitalLog())
                .build();
    }
    
    // 상세 로그 조회 메서드
    public List<DetailLogResponseDto> getDetailLog(
        UserPrincipal userPrincipal,
        String petName){
            PetEntity petEntity = familyJpaRepository.findByUserIdAndPetName(userPrincipal.getId(), petName)
                .orElseThrow(() -> new RuntimeException("Pet not found with userId: " 
                                                      + userPrincipal.getId() 
                                                      + " & petName: " + petName));

            Long petId = petEntity.getId();
            List<DetaillogEntity> detailLogs = detaillogJpaRepository.findAllByPetId(petId);
            if (detailLogs.isEmpty()) {
                throw new RuntimeException("No detail logs found with petId: " + petId);
            }


            return detailLogs.stream().map(detailLog -> DetailLogResponseDto.builder()
                .log_id(detailLog.getLog_id().getId())      
                .log_time(detailLog.getLog_time())
                .mealType(detailLog.getMeal_type())         
                .place(detailLog.getPlace())
                .price(detailLog.getPrice())
                .take_time(detailLog.getTake_time())
                .memo(detailLog.getMemo())
                .build()
            ).toList();
    }

    public List<HealthLogResponseDto> getHealthLog(
        UserPrincipal userPrincipal,
        String petName
    ){
            PetEntity petEntity = familyJpaRepository.findByUserIdAndPetName(userPrincipal.getId(), petName)
                .orElseThrow(() -> new RuntimeException("Pet not found with userId: " 
                                                      + userPrincipal.getId() 
                                                      + " & petName: " + petName));

            Long petId = petEntity.getId();
            List<HealthlogEntity> healthLogs = healthlogJpaRepository.findAllByPetId(petId);
            if (healthLogs.isEmpty()) {
                throw new RuntimeException("No detail logs found with petId: " + petId);
            }

            return healthLogs.stream().map(healthLog -> HealthLogResponseDto.builder()
                .vaccination(healthLog.getVaccination())
                .vaccination_log(healthLog.getVaccination_log())
                .hospital(healthLog.getHospital())
                .hospital_log(healthLog.getHospital_log())
                .build()
            ).toList();
    }

    @Transactional
    public void patchDetailLogs(
        UserPrincipal userPrincipal,
        PetLogDetailLogPatchDto petLogDetailLogPatchDto
    ){
        String    petName    = petLogDetailLogPatchDto.getPetName();
        Long      userId     = userPrincipal.getId();
        Type      oldType    = petLogDetailLogPatchDto.getOldType();
        LocalDateTime oldLogTime = petLogDetailLogPatchDto.getOldLogTime();

        DetaillogEntity detail = detaillogJpaRepository
            .findByPetNameAndUserIdAndTypeAndLogTime(petName, userId, oldType, oldLogTime)
            .orElseThrow(() -> new RuntimeException(
                String.format("로그를 찾을 수 없습니다 (petName=%s, userId=%d, type=%s, logTime=%s)",
                              petName, userId, oldType, oldLogTime)));

        Type newType = petLogDetailLogPatchDto.getNewType();
        if (newType != null && newType != oldType) {
            PetlogEntity petlog = detail.getLog_id();
            petlog.setType(newType);
            petlogJpaRepository.save(petlog);
        }

        if (petLogDetailLogPatchDto.getNewLogTime() != null) detail.setLog_time(petLogDetailLogPatchDto.getNewLogTime());
        if (petLogDetailLogPatchDto.getMealType()   != null) detail.setMeal_type(petLogDetailLogPatchDto.getMealType());
        if (petLogDetailLogPatchDto.getPlace()      != null) detail.setPlace(petLogDetailLogPatchDto.getPlace());
        if (petLogDetailLogPatchDto.getPrice()      != null) detail.setPrice(petLogDetailLogPatchDto.getPrice());
        if (petLogDetailLogPatchDto.getTakeTime()   != null) detail.setTake_time(petLogDetailLogPatchDto.getTakeTime());
        if (petLogDetailLogPatchDto.getMemo()       != null) detail.setMemo(petLogDetailLogPatchDto.getMemo());
        
        detaillogJpaRepository.save(detail);
    }

    @Transactional
    public void patchHealthLogs(
        UserPrincipal userPrincipal,
        HealthLogPatchDto dto
        ){
            PetEntity pet = familyJpaRepository
            .findByUserIdAndPetName(userPrincipal.getId(), dto.getPetName())
            .orElseThrow(() -> new RuntimeException(
                "Pet not found: " + dto.getPetName()));

                HealthlogEntity healthlog = healthlogJpaRepository
                .findByPetIdAndHospitalLog(pet.getId(), dto.getOldHospitalLog())
                .orElseThrow(() -> new RuntimeException(
                    "HealthLog not found for pet=" + dto.getPetName()
                  + " at date=" + dto.getOldHospitalLog()));

            boolean hasVaccine = dto.getVaccination()    != null
                              || dto.getVaccinationLog() != null;

             if (hasVaccine) {
            // — 예방접종 정보 우선 반영
            if (dto.getVaccination()    != null) healthlog.setVaccination(dto.getVaccination());
            if (dto.getVaccinationLog() != null) healthlog.setVaccination_log(dto.getVaccinationLog());
            // — 그 외 필드도 null 체크 후 반영
            if (dto.getHospital()   != null) healthlog.setHospital(dto.getHospital());
            if (dto.getHospitalLog() != null) healthlog.setHospital_log(dto.getHospitalLog());
             }
            else {
            // 예방접종 정보 없으면, 병원 방문 정보만
            if (dto.getHospital()   != null) healthlog.setHospital(dto.getHospital());
            if (dto.getHospitalLog() != null) healthlog.setHospital_log(dto.getHospitalLog());
        }

            healthlogJpaRepository.save(healthlog);
        }
    @Transactional
    public void deleteDetailLogs(UserPrincipal userPrincipal, String petName) {
        PetEntity pet = familyJpaRepository
            .findByUserIdAndPetName(userPrincipal.getId(), petName)
            .orElseThrow(() -> new RuntimeException("Pet not found: " + petName));
        detaillogJpaRepository.deleteAllByPetId(pet.getId());
    }

    @Transactional
    public void deleteHealthLogs(UserPrincipal userPrincipal, String petName) {
        PetEntity pet = familyJpaRepository
            .findByUserIdAndPetName(userPrincipal.getId(), petName)
            .orElseThrow(() -> new RuntimeException("Pet not found: " + petName));
        healthlogJpaRepository.deleteAllByPetId(pet.getId());
    }        
        
    }
