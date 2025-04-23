package com.example.plog.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.plog.repository.user.UserEntity;
import com.example.plog.web.dto.user.UserRegistrationDto;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target="id", ignore = true)
    @Mapping(target="familyList", ignore = true)
    @Mapping(target="profileImage", ignore = true)
    UserEntity userRegistrationDtoToUserEntity(UserRegistrationDto userRegistrationDto);
}
