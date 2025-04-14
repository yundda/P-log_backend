package com.example.plog.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.plog.repository.pet.PetEntity;
import com.example.plog.web.dto.PetProfileDto;

@Mapper
public interface PetProfileMapper {
    PetProfileMapper INSTANCE = Mappers.getMapper(PetProfileMapper.class);
    
    
    @Mapping(source = "petName", target = "name")
    @Mapping(source = "petSpecies", target = "species")
    @Mapping(source = "petBreed", target = "breed")
    @Mapping(source = "petBirthday", target = "birthday")
    @Mapping(source = "petGender", target = "gender")
    @Mapping(source = "petWeight", target = "weight")
    @Mapping(source = "petPhoto", target = "photo")
    @Mapping(target = "familyList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "familyList", ignore = true)
    PetEntity petProfileDtoToPetEntity(PetProfileDto dto);


    // PetProfileDto petEntityToPetProfileDto(PetEntity entity);
}
