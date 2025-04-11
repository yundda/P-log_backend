package com.example.plog.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.plog.repository.pet.PetEntity;
import com.example.plog.web.dto.PetProfileDto;

@Mapper
public interface PetProfileMapper {
    PetProfileMapper INSTANCE = Mappers.getMapper(PetProfileMapper.class);
    
    @Mapping(target = "id", ignore = true)
    PetEntity petProfileDtoToPetEntity(PetProfileDto dto);


    // PetProfileDto petEntityToPetProfileDto(PetEntity entity);
}
