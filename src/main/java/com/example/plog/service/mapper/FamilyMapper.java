package com.example.plog.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.plog.repository.Enum.Role;
import com.example.plog.repository.family.FamilyEntity;

@Mapper
public interface FamilyMapper {
    FamilyMapper INSTANCE = Mappers.getMapper(FamilyMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "pet.id", source = "petId")
    FamilyEntity userIdAndPetIdAndRoleToFamilyEntity(Long userId, Long petId, Role role);
}
