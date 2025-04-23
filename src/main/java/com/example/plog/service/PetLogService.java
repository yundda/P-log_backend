package com.example.plog.service;

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
import com.example.plog.service.exceptions.AuthorizationException;
import com.example.plog.service.exceptions.DatabaseException;
import com.example.plog.service.exceptions.NotFoundException;
import com.example.plog.service.resolver.EntityFinder;
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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Autowired
    EntityFinder entityFinder;

    
    // DetailLog 생성 메서드
    public PetLogDto createDetailLog(PetLogDetailLogDto petLogDetailLogDto) {
        String petName = petLogDetailLogDto.getPetlog().getName();
        PetEntity petEntity = entityFinder.getPetByPetName(petName);
        FamilyEntity familyEntity = entityFinder.getFamilyById(petEntity.getId());

        Long userId = familyEntity.getUser().getId();

        petLogDetailLogDto.getPetlog().setPetId(petEntity.getId());
        petLogDetailLogDto.getPetlog().setUserId(userId);

        PetlogEntity petlogEntity = convertToPetlogEntity(petLogDetailLogDto.getPetlog());
        PetlogEntity savedPetlog = petlogJpaRepository.save(petlogEntity);
        DetaillogEntity detailLogEntity = null;
        
        // PetLog의 타입이 "HOSPITAL"이 아닌 경우 상세 로그 생성
        if (!savedPetlog.getType().equals(Type.HOSPITAL)) {
            detailLogEntity = convertToDetaillogEntity(petLogDetailLogDto.getDetailLog());
            detailLogEntity.setLog(savedPetlog);
            detaillogJpaRepository.save(detailLogEntity);
        }

        // 생성된 PetLog와 DetailLog 정보를 PetLogDto로 반환
        return PetLogDto.builder()
                .petId(petlogEntity.getPet().getId())
                .userId(petlogEntity.getUser().getId())
                .type(petlogEntity.getType())
                .logId(detailLogEntity != null ? detailLogEntity.getLog().getId() : null)
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
                .pet(PetEntity.builder().id(petLogDto.getPetId()).build())      
                .user(UserEntity.builder().id(petLogDto.getUserId()).build())
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
        PetEntity petEntity = entityFinder.getPetByPetName(petName);
        FamilyEntity familyEntity = entityFinder.getFamilyById(petEntity.getId());

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
            healthlogEntity.setLog(savedPetlog);
            healthlogJpaRepository.save(healthlogEntity);
        }

        // 생성된 PetLog와 HealthLog 정보를 PetLogDto로 반환
        return PetLogDto.builder()
        .petId(petlogEntity.getPet().getId())
        .userId(petlogEntity.getUser().getId())
        .type(petlogEntity.getType())
        .logId(healthlogEntity != null ? healthlogEntity.getLog().getId() : null)
        .logDate(healthlogEntity != null ? healthlogEntity.getHospital_log() : null)
        .build();
    }

    // PetLogDtoForHealth를 PetlogEntity로 변환하는 메서드
    private PetlogEntity convertToPetlogEntity(PetLogDtoForHealth petLogDtoForHealth) {
        return PetlogEntity.builder()
                .pet(PetEntity.builder().id(petLogDtoForHealth.getPetId()).build())      
                .user(UserEntity.builder().id(petLogDtoForHealth.getUserId()).build())
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
            PetEntity petEntity = entityFinder.getPetByUserIdAndPetName(userPrincipal.getId(), petName);
            Long petId = petEntity.getId();
            List<DetaillogEntity> detailLogs = detaillogJpaRepository.findAllByPetId(petId);
            if (detailLogs.isEmpty()) {
                throw new NotFoundException("요청한 데이터를 찾을 수 없습니다 : " + petId);
            }


            return detailLogs.stream().map(detailLog -> DetailLogResponseDto.builder()
                .log_id(detailLog.getId())      
                .log_time(detailLog.getLog_time())
                .mealType(detailLog.getMeal_type())         
                .place(detailLog.getPlace())
                .price(detailLog.getPrice())
                .take_time(detailLog.getTake_time())
                .memo(detailLog.getMemo())
                .type(detailLog.getLog().getType())
                .build()
            ).toList();
    }

    // HealthLog 조회
    public List<HealthLogResponseDto> getHealthLog(
        UserPrincipal userPrincipal,
        String petName
    ){
            PetEntity petEntity = entityFinder.getPetByUserIdAndPetName(userPrincipal.getId(), petName);
            Long petId = petEntity.getId();
            List<HealthlogEntity> healthLogs = healthlogJpaRepository.findAllByPetId(petId);

            return healthLogs.stream().map(healthLog -> HealthLogResponseDto.builder()
                .log_id(healthLog.getId())
                .vaccination(healthLog.getVaccination())
                .vaccination_log(healthLog.getVaccination_log())
                .hospital(healthLog.getHospital())
                .hospital_log(healthLog.getHospital_log())
                .build()
            ).toList();
    }

    // DetailLog 수정
    @Transactional
    public void patchDetailLogs(
        UserPrincipal userPrincipal,
        PetLogDetailLogPatchDto dto
    ){
        DetaillogEntity detail = detaillogJpaRepository.findById(dto.getLog_id())
        .orElseThrow(() -> new NotFoundException("요청한 데이터를 찾을 수 없습니다 : id=" + dto.getLog_id()));

        Long ownerId = detail.getLog().getUser().getId();
        if (!ownerId.equals(userPrincipal.getId())) {
        throw new AuthorizationException("접근 권한이 없습니다.");
        }

        if (dto.getNewType() != null && dto.getNewType() != detail.getLog().getType()) {
            PetlogEntity petlog = detail.getLog();
            petlog.setType(dto.getNewType());
            try {
                petlogJpaRepository.save(petlog);
            } catch (Exception e) {
                throw new DatabaseException("요청한 데이터를 찾을 수 없습니다 : " + e.getMessage());
            }
        }

        try {
            if (dto.getNewLogTime() != null) detail.setLog_time(dto.getNewLogTime());
            if (dto.getMealType()   != null) detail.setMeal_type(dto.getMealType());
            if (dto.getPlace()      != null) detail.setPlace(dto.getPlace());
            if (dto.getPrice()      != null) detail.setPrice(dto.getPrice());
            if (dto.getTakeTime()   != null) detail.setTake_time(dto.getTakeTime());
            if (dto.getMemo()       != null) detail.setMemo(dto.getMemo());

            detaillogJpaRepository.save(detail);
        } catch (Exception e) {
            throw new DatabaseException("DB를 수정하는 중 오류가 발생했습니다. : " + e.getMessage());
        }
    }

    // HealthLog 수정
    @Transactional
    public void patchHealthLogs(
        UserPrincipal userPrincipal,
        HealthLogPatchDto dto
        ){
            HealthlogEntity healthlog = healthlogJpaRepository.findById(dto.getLog_id())
            .orElseThrow(() -> new NotFoundException("요청한 데이터를 찾을 수 없습니다 : id=" + dto.getLog_id()));

            Long ownerId = healthlog.getLog()
            .getUser().getId(); 
            if (!ownerId.equals(userPrincipal.getId())) {
            throw new AuthorizationException("접근 권한이 없습니다.");
}

            try {
                if (dto.getVaccination()    != null) healthlog.setVaccination(dto.getVaccination());
                if (dto.getVaccinationLog() != null) healthlog.setVaccination_log(dto.getVaccinationLog());
                if (dto.getHospital()       != null) healthlog.setHospital(dto.getHospital());
                if (dto.getHospitalLog()    != null) healthlog.setHospital_log(dto.getHospitalLog());

                healthlogJpaRepository.save(healthlog);
            } catch (Exception e) {
                throw new DatabaseException("DB를 수정하는 중 오류가 발생했습니다. : " + e.getMessage());
            }
        }

    // Detailog 삭제
    @Transactional
    public void deleteDetailLog(UserPrincipal user, Long logId) {
        // 1) ID로 조회
        DetaillogEntity detail = detaillogJpaRepository.findById(logId)
        .orElseThrow(() -> new NotFoundException("요청한 데이터를 찾을 수 없습니다 : id=" + logId));

        // 2) 삭제
        try {
            detaillogJpaRepository.delete(detail);
            petlogJpaRepository.deleteById(detail.getLog().getId());
        } catch (Exception e) {
            throw new DatabaseException("DB를 삭제하는 중 오류가 발생했습니다 : " + e.getMessage());
        }
    }

    // HealthLog 삭제
    @Transactional
    public void deleteHealthLog(UserPrincipal user, Long LogId) {
        // 1) ID로 조회
        HealthlogEntity health = healthlogJpaRepository.findById(LogId)
        .orElseThrow(() -> new NotFoundException("요청한 데이터를 찾을 수 없습니다 : id=" + LogId));

        // 2) 삭제
        try {
            healthlogJpaRepository.delete(health);
            petlogJpaRepository.deleteById(health.getLog().getId());
        } catch (Exception e) {
            throw new DatabaseException("DB를 삭제하는 중 오류가 발생했습니다 : " + e.getMessage());
        }
    }        
        
    }
