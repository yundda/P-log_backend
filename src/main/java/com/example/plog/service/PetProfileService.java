package com.example.plog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.pet.PetJpaRepository;
import com.example.plog.security.UserPrincipal;
import com.example.plog.service.mapper.PetProfileMapper;
import com.example.plog.web.dto.PetProfileDto;
import com.example.plog.web.dto.pet.PetResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetProfileService{

    @Autowired
    PetJpaRepository petJpaRepository;
    
    public PetResponseDto createPet(UserPrincipal userPrincipal, PetProfileDto petProfileDto) {
        Long userId = userPrincipal.getId();
        // userId에 맞게 petProfileDto의 ownerId를 설정 or familyID 등등....
        PetEntity petEntity = PetProfileMapper.INSTANCE.petProfileDtoToPetEntity(petProfileDto);
        petJpaRepository.save(petEntity);
        return PetResponseDto.builder()
                    .petId(petEntity.getId())
                    .petName(petEntity.getName())
                    .build();
    }

    
    // public void modify(PetProfileDto dto){
    //     PetEntity entity = petJpaRepository.findById(dto.getId())
    //     .orElseThrow(() -> new RuntimeException("Pet not found with id: " + dto.getId()));

    //     if(dto.getName()!=null){
    //         entity.setName(dto.getName());
    //     }
    //     if(dto.getSpecies()!=null){
    //         entity.setSpecies(dto.getSpecies());
    //     }
    //     if(dto.getBreed()!=null){
    //         entity.setBreed(dto.getBreed());
    //     }
    //     if(dto.getBirthday()!=null){
    //         entity.setBirthday(dto.getBirthday());
    //     }
    //     if(dto.getGender()!=null){
    //         entity.setGender(dto.getGender());
    //     }
    //     if(dto.getWeight()!=null){
    //         entity.setWeight(dto.getWeight());
    //     }
    //     if(dto.getPhoto()!=null){
    //         entity.setPhoto(dto.getPhoto());
    //     }
    //     petJpaRepository.save(entity);
    // }

    // public void remove(Long id){
    //     petJpaRepository.deleteById(id);
    // }
}
