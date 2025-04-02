package com.example.plog.service;

import com.example.plog.dto.UserDto;
import com.example.plog.repository.entity.UserEntity;

public interface UserService {

    Long register(UserDto dto);
    UserDto read(Long gno);
    void modify(UserDto dto);
    void remove(UserDto dto);

    default UserEntity dtoToEntity(UserDto dto) {
        return UserEntity.builder()
                .gno(dto.getGno())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .build();
    }
    
    default UserDto entityToDto(UserEntity entity) {
        return UserDto.builder()
                .gno(entity.getGno())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .password(entity.getPassword())
                .build();
    }

}
