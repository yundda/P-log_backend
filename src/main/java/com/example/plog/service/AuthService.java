package com.example.plog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.plog.repository.user.UserEntity;
import com.example.plog.repository.user.UserJpaRepository;
import com.example.plog.security.TokenProvider;
import com.example.plog.service.exceptions.DatabaseException;
import com.example.plog.service.exceptions.InvalidValueException;
import com.example.plog.service.exceptions.NotFoundException;
import com.example.plog.service.mapper.UserMapper;
import com.example.plog.web.dto.user.UserLoginDto;
import com.example.plog.web.dto.user.UserRegistrationDto;
import com.example.plog.web.dto.user.UserResponseDto;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AuthService {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserResponseDto userResponseDto;

    public UserResponseDto registerUser(UserRegistrationDto userRegistrationDto) {
        // 이메일 중복 확인
        if (userJpaRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
            throw new InvalidValueException("이미 사용중인 이메일입니다.");
        }
        // 닉네임 중복 확인
        if (userJpaRepository.findByNickname(userRegistrationDto.getNickname()).isPresent()) {
            throw new InvalidValueException("이미 사용중인 닉네임입니다.");
        }
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());
        userRegistrationDto.setPassword(encodedPassword);
        // 사용자 정보 저장
        UserEntity userEntity = UserMapper.INSTANCE.userRegistrationDtoToUserEntity(userRegistrationDto);
        try{
            userJpaRepository.save(userEntity);
        }catch (Exception e){
            throw new DatabaseException("회원가입에 실패했습니다.");
        }
        userResponseDto.setNickname(userEntity.getNickname());
        return userResponseDto;

    }

    public UserResponseDto createToken(UserLoginDto userRegistrationDto) {
        // 사용자 정보 조회
        UserEntity user = userJpaRepository.findByEmail(userRegistrationDto.getEmail()).orElseThrow(() -> {
            throw new NotFoundException("해당 이메일의 사용자를 찾을 수 없습니다.");
        });
        // 비밀번호 확인
        if (!passwordEncoder.matches(userRegistrationDto.getPassword(), user.getPassword())) {
            throw new NotFoundException("비밀번호가 일치하지 않습니다.");
        }
        // 토큰 생성
        try{final String token = tokenProvider.createToken(user);
            log.info("token: {}", token);
            userResponseDto.setToken(token);
            userResponseDto.setNickname(user.getNickname());
            return userResponseDto;
        }catch (Exception e){
            log.error("토큰 생성 중 오류 발생: {}", e.getMessage());
            throw new DatabaseException("로그인 및 토큰 생성에 실패했습니다.");
        }
    }
    
}
