package com.example.plog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.plog.repository.user.UserJpaRepository;
import com.example.plog.security.TokenProvider;
import com.example.plog.web.dto.UserInfoDto;
import com.example.plog.web.dto.UserLoginDto;
import com.example.plog.web.dto.UserRegistrationDto;

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

    public UserInfoDto registerUser(UserRegistrationDto userRegistrationDto) {
        throw new UnsupportedOperationException("Unimplemented method 'registerUser'");
    }

    public String createToken(UserLoginDto userRegistrationDto) {
        throw new UnsupportedOperationException("Unimplemented method 'createToken'");
    }
    
}
