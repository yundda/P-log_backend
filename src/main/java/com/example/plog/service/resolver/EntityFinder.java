package com.example.plog.service.resolver;


import com.example.plog.repository.Enum.Role;
import com.example.plog.repository.family.FamilyEntity;
import com.example.plog.repository.family.FamilyJpaRepository;
import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.pet.PetJpaRepository;
import com.example.plog.repository.user.UserEntity;
import com.example.plog.repository.user.UserJpaRepository;
import com.example.plog.service.exceptions.InvalidValueException;
import com.example.plog.service.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EntityFinder {
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    PetJpaRepository petJpaRepository;
    @Autowired
    FamilyJpaRepository familyJpaRepository;

    public UserEntity getUserById(Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }
    public UserEntity getUserByNickname(String nickname) {
        return userJpaRepository.findByNickname(nickname)
            .orElseThrow(() -> new NotFoundException("해당 닉네임의 사용자를 찾을 수 없습니다."));
    }
    public PetEntity getPetByPetName(String petName) {
        return petJpaRepository.findByPetName(petName)
            .orElseThrow(() -> new NotFoundException("해당 이름의 펫을 찾을 수 없습니다."));
    }
    public FamilyEntity getFamilyById(Long familyId) {
        return familyJpaRepository.findById(familyId)
            .orElseThrow(() -> new NotFoundException("해당 ID의 가족을 찾을 수 없습니다."));
    }
    public PetEntity getPetByUserIdAndPetName(Long userId, String petName) {
        return familyJpaRepository.findPetByUserIdAndPetName(userId, petName)
            .orElseThrow(() -> new NotFoundException("해당 펫을 찾을 수 없습니다."));
    }

    public PetEntity findPetOrThrowIfNotFoundOrIfNotOwner(String petName, UserEntity receiver) {
        if (!petJpaRepository.existsByPetName(petName)) {
            throw new NotFoundException("해당 이름의 펫을 찾을 수 없습니다.");
        }

        List<UserEntity> ownerList = familyJpaRepository.findByPetNameAndRole(petName, Role.OWNER);
        log.info("ownerList:{}", ownerList);

        if (ownerList.isEmpty()) {
            throw new NotFoundException("해당 펫의 대표관리자가 없습니다.");
        } else if (!ownerList.contains(receiver)) {
            throw new InvalidValueException("유저가 해당 펫의 대표 관리자가 아닙니다.");
        }

        return familyJpaRepository.findPetByUserIdAndPetName(receiver.getId(), petName)
            .orElseThrow(() -> new NotFoundException("해당 펫을 찾을 수 없습니다."));
    }
}
