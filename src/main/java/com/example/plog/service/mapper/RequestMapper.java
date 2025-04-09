package com.example.plog.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.plog.repository.request.RequestEntity;

@Mapper
public interface RequestMapper {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requester.id", source = "userId")
    @Mapping(target = "receiver.id", source = "ownerId")
    @Mapping(target = "pet.id", source = "petId")
    @Mapping(target = "status", expression = "java(com.example.plog.repository.Enum.Status.PENDING)")
    RequestEntity userIdAndOwnerIdIdAndPetIdtoRequestEntity(Long userId, Long ownerId, Long petId);

    // @Named("mapPet")
    // default Pet mapPet(Long petId) {
    //     // Pet 객체를 petId를 기반으로 생성하거나 조회
    //     Pet pet = new Pet();
    //     pet.setId(petId);
    //     return pet;
    // }
    
}
