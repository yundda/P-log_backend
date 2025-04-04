package com.example.plog.service.mapper;

import com.example.plog.repository.pet.PetEntity;
import com.example.plog.web.dto.PetProfileDto;

public interface PetProfileMapper {

    default PetEntity dtoToEntity(PetProfileDto dto){
        System.out.println("sdfghjk");
        if(dto == null){
            return null;
        }
        return PetEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .species(dto.getSpecies())
                .breed(dto.getBreed())
                .birthday(dto.getBirthday())
                .gender(dto.getGender())
                .weight(dto.getWeight())
                .photo(dto.getPhoto())
                .build();
    }

    default PetProfileDto entityToDto(PetEntity entity) {
        if (entity == null) {
            return null;
        }
        return PetProfileDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .species(entity.getSpecies())
                .breed(entity.getBreed())
                .birthday(entity.getBirthday())
                .gender(entity.getGender())
                .weight(entity.getWeight())
                .photo(entity.getPhoto())
                .build();
    }
}
