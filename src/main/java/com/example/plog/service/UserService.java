package com.example.plog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.plog.repository.user.UserEntity;
import com.example.plog.repository.user.UserJpaRepository;
import com.example.plog.service.exceptions.DatabaseException;
import com.example.plog.service.exceptions.InvalidValueException;
import com.example.plog.service.exceptions.NotFoundException;
import com.example.plog.service.mapper.UserMapper;
import com.example.plog.web.dto.user.UserResponseDto;
import com.example.plog.web.dto.user.UserUpdateDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    UserResponseDto userResponseDto;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponseDto getUserInfo(Long userId) {
        // 사용자 정보 조회
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
        UserResponseDto user = UserMapper.INSTANCE.userEntityToUserResponseDto(userEntity);
        return user;
    }

    public void updateUser(Long userId, UserUpdateDto updateInfo) {
        // 사용자 정보 조회
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
        // 비밀번호 확인
        if (updateInfo.getBeforePassword() != null) {
            if (!passwordEncoder.matches(updateInfo.getBeforePassword(), userEntity.getPassword())) {
                throw new InvalidValueException("비밀번호가 일치하지 않습니다.");
            }
        }
        // 사용자 정보 유효성 검사
        if (updateInfo.getNickname() == null && updateInfo.getAfterPassword() == null) {
            throw new InvalidValueException("변경할 정보가 없습니다.");
        }
        
        String updateNick = updateInfo.getNickname();
        String updatePassword = updateInfo.getAfterPassword();


        // 닉네임 중복 확인
        if (updateNick != null && userJpaRepository.findByNickname(updateNick).isPresent()) {
            throw new InvalidValueException("이미 사용중인 닉네임입니다.");
        }else if (updateNick != null) {
            userEntity.setNickname(updateNick);
        }
        // 비밀번호 암호화 후 저장
        if(updatePassword != null && updatePassword.length() < 8) {
            throw new InvalidValueException("비밀번호는 8자 이상이어야 합니다.");
        }else if (updatePassword != null){
            String encodedPassword = passwordEncoder.encode(updatePassword);
            userEntity.setPassword(encodedPassword);
        }
        // DB 저장
        try {
            userJpaRepository.save(userEntity);
        } catch (Exception e) {
            log.error("Error Update User: {}", e.getMessage());
            throw new DatabaseException("사용자 정보 업데이트에 실패했습니다.");
        }
    }
    
}
