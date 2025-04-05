package com.example.plog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plog.repository.user.UserEntity;
import com.example.plog.repository.user.UserJpaRepository;
import com.example.plog.service.exceptions.InvalidValueException;
import com.example.plog.service.mapper.UserMapper;
import com.example.plog.web.dto.UserResponseDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    UserResponseDto userResponseDto;

    public UserResponseDto getUserInfo(Long userId) {
        // 사용자 정보 조회
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(() -> {
            throw new InvalidValueException("해당 사용자를 찾을 수 없습니다.");
        });
        UserResponseDto user = UserMapper.INSTANCE.userEntityToUserResponseDto(userEntity);
        return user;
    }
    
}
