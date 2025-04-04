package com.example.plog.service.mapper;

import com.example.plog.web.dto.UserDto;
import com.example.plog.repository.entity.UserEntity;

public interface UserMapper {

    Long register(UserDto dto);
    UserDto read(Long id);
    void modify(UserDto dto);
    void remove(UserDto dto);

    default UserEntity dtoToEntity(UserDto dto) {
        return UserEntity.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .build();
    }
    
    default UserDto entityToDto(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .password(entity.getPassword())
                .build();
    }

}
