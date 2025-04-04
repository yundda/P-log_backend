package com.example.plog.service;

import org.springframework.stereotype.Service;

import com.example.plog.repository.Interface.UserRepository;
import com.example.plog.security.TokenProvider;
import com.example.plog.web.dto.UserInfoDto;
import com.example.plog.web.dto.UserLoginDto;
import com.example.plog.web.dto.UserRegistrationDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    // private final UserRepository userRepository;
    // private final TokenProvider tokenProvider;
    // private PasswordEncoder passwordEncoder;

    public UserInfoDto registerUser(UserRegistrationDto userRegistrationDto) {
        throw new UnsupportedOperationException("Unimplemented method 'registerUser'");
    }

    public String createToken(UserLoginDto userRegistrationDto) {
        throw new UnsupportedOperationException("Unimplemented method 'createToken'");
    }
    
}
