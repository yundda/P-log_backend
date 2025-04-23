package com.example.plog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.plog.repository.family.FamilyJpaRepository;
import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.pet.PetJpaRepository;
import com.example.plog.repository.user.UserEntity;
import com.example.plog.repository.user.UserJpaRepository;
import com.example.plog.security.TokenProvider;
import com.example.plog.security.UserPrincipal;
import com.example.plog.service.exceptions.DatabaseException;
import com.example.plog.service.exceptions.InvalidValueException;
import com.example.plog.service.resolver.EntityFinder;
import com.example.plog.web.dto.user.FamilyInfoDto;
import com.example.plog.web.dto.user.UserResponseDto;
import com.example.plog.web.dto.user.UserUpdateDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PetJpaRepository petJpaRepository;

    @Autowired
    FamilyJpaRepository familyJpaRepository;

    @Autowired
    EntityFinder entityFinder;

    @Autowired
    TokenProvider tokenProvider;

    public UserResponseDto getUserInfo(UserPrincipal userPrincipal) {
        // 사용자 정보 조회
        UserEntity user = entityFinder.getUserById(userPrincipal.getId());
        return UserResponseDto.builder()
            .userId(user.getId())
            .nickname(user.getNickname())
            .email(user.getEmail())
            .profileImage(user.getProfileImage())
            .build();
    }
    @Transactional
    public void updateUser(UserPrincipal userPrincipal, UserUpdateDto updateInfo) {
        // 사용자 정보 조회
        try {
            UserEntity user = entityFinder.getUserById(userPrincipal.getId());
            String updateNick = updateInfo.getNickname();
            String updatePassword = updateInfo.getAfterPassword();

            // 비밀번호 확인
            if(updateInfo.getBeforePassword() == null){
                throw new InvalidValueException("기존 비밀번호를 입력해주세요.");
            }else if(!passwordEncoder.matches(updateInfo.getBeforePassword(), user.getPassword())) {
                    throw new InvalidValueException("기존 비밀번호와 일치하지 않습니다.");
            }
            // 사용자 정보 유효성 검사
            if (updateNick == null && updatePassword == null) {
                throw new InvalidValueException("변경할 정보가 없습니다.");
            }else if(updateNick != null && updateNick.equals(user.getNickname())){
                throw new InvalidValueException("닉네임이 변경되지 않았습니다.");
            }
            // 닉네임 중복 확인
            if (updateNick != null && userJpaRepository.findByNickname(updateNick).isPresent()) {
                throw new InvalidValueException("이미 사용중인 닉네임입니다.");
            }else if (updateNick != null) {
                user.setNickname(updateNick);
            }
            // 비밀번호 암호화 후 저장
            if(updatePassword != null ) {
                String encodedPassword = passwordEncoder.encode(updatePassword);
                user.setPassword(encodedPassword);
            }

        } catch (DataAccessException e) {
            log.error("사용자 정보 DB 업데이트 오류: {}", e.getMessage());
            throw new DatabaseException("사용자 정보 DB 업데이트에 실패했습니다.");
        }
    }

    public void updateProfileImage(UserPrincipal userPrincipal, String profileImage) {
        System.out.println(profileImage);
        // 사용자 정보 조회
        UserEntity user = entityFinder.getUserById(userPrincipal.getId());
        // 프로필 이미지 업데이트
        try {
            user.setProfileImage(profileImage);
            userJpaRepository.save(user);
        } catch (DataAccessException e) {
            log.error("프로필 이미지 DB 업데이트 오류: {}", e.getMessage());
            throw new DatabaseException("사용자 프로필 이미지 DB 업데이트에 실패했습니다.");
        }
    }

    @Transactional
    public void leavePet(UserPrincipal userPrincipal, String petName) {
        try {
        UserEntity user = entityFinder.getUserById(userPrincipal.getId());
        // 펫 정보 조회
        PetEntity pet = entityFinder.getPetByUserIdAndPetName(user.getId(), petName);
        // 펫 삭제
            familyJpaRepository.deleteByUserAndPet(user,pet);
        } catch (DataAccessException e) {
            log.error("가족에서 빠지기 DB 오류: {}", e.getMessage());
            throw new DatabaseException("가족에서 빠지기 DB 업데이트에 실패했습니다.");
        }
    }

    public UserResponseDto getFamilyList(UserPrincipal userPrincipal, String petName) {
            UserEntity user = entityFinder.getUserById(userPrincipal.getId());
            PetEntity pet = entityFinder.getPetByUserIdAndPetName(user.getId(), petName);
            // List<UserEntity> families = familyJpaRepository.findFamilyListByUserIdAndPetName(user.getId(),pet.getPetName());
            List<FamilyInfoDto> families = pet.getFamilyList().stream()
                    .filter(family -> !family.getUser().getId().equals(user.getId()))
                    .map((family)-> {
                        UserEntity member = family.getUser();
                        return FamilyInfoDto.builder()
                            .nickName(member.getNickname())
                            .profileImage(member.getProfileImage())
                            .build();
                    })
                    .collect(Collectors.toList());
            return UserResponseDto.builder()
                .petName(pet.getPetName())
                .familyList(families)
                .build();
    }
}
