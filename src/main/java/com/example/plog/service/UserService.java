package com.example.plog.service;

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
import com.example.plog.service.exceptions.NotFoundException;
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
    TokenProvider tokenProvider;

    public UserEntity getUserById(Long userId) {
        return userJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }

    public UserEntity getUserByNickname(String nickname) {
        return userJpaRepository.findByNickname(nickname)
            .orElseThrow(() -> new NotFoundException("해당 닉네임의 사용자를 찾을 수 없습니다."));
    }

    public UserResponseDto getUserInfo(UserPrincipal userPrincipal) {
        // 사용자 정보 조회
        UserEntity user = getUserById(userPrincipal.getId());
        return UserResponseDto.builder()
            .userId(user.getId())
            .nickname(user.getNickname())
            .email(user.getEmail())
            .build();
    }
    @Transactional
    public void updateUser(UserPrincipal userPrincipal, UserUpdateDto updateInfo) {
        // 사용자 정보 조회
        try {
            UserEntity user = getUserById(userPrincipal.getId());
            String updateNick = updateInfo.getNickname();
            String updatePassword = updateInfo.getAfterPassword();

            // 비밀번호 확인
            if(updateInfo.getBeforePassword() == null){
                throw new InvalidValueException("기존 비밀번호를 입력해주세요.");
            }else if(!passwordEncoder.matches(updateInfo.getBeforePassword(), user.getPassword())) {
                    throw new InvalidValueException("기존 비밀번호를 일치하지 않습니다.");
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
            log.error("Error Update User: {}", e.getMessage());
            throw new DatabaseException("사용자 정보 DB 업데이트에 실패했습니다.");
        }
    }
    @Transactional
    public void leavePet(UserPrincipal userPrincipal, String petName) {
        try {
        UserEntity user = getUserById(userPrincipal.getId());
        // 펫 정보 조회
        PetEntity pet = familyJpaRepository.findByUserIdAndPetName(user.getId(), petName)
            .orElseThrow(() -> new NotFoundException("해당 펫은 사용자의 펫이 아닙니다."));
        // 펫 삭제
            familyJpaRepository.deleteByUserAndPet(user,pet);
        } catch (DataAccessException e) {
            log.error("가족에서 빠지기 DB 오류: {}", e.getMessage());
            throw new DatabaseException("가족에서 빠지기 DB 업데이트에 실패했습니다.");
        }
    }
}
