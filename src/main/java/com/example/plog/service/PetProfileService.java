package com.example.plog.service;

import org.springframework.stereotype.Component;

import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.pet.PetJpaRepository;
import com.example.plog.service.mapper.PetProfileMapper;
import com.example.plog.web.dto.PetProfileDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PetProfileService implements PetProfileMapper {

    private final PetJpaRepository petJpaRepository;

    
    public Long register(PetProfileDto dto){
        PetEntity entity = dtoToEntity(dto);
        System.out.println(entity.getGender());
        PetEntity saved = petJpaRepository.save(entity);
        return saved.getId();
    }

    
    public PetProfileDto read(Long id) {
        PetEntity entity = petJpaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
    return entityToDto(entity);
    }

    
    public void modify(PetProfileDto dto){
        PetEntity entity = petJpaRepository.findById(dto.getId())
        .orElseThrow(() -> new RuntimeException("Pet not found with id: " + dto.getId()));

        if(dto.getName()!=null){
            entity.setName(dto.getName());
        }
        if(dto.getSpecies()!=null){
            entity.setSpecies(dto.getSpecies());
        }
        if(dto.getBreed()!=null){
            entity.setBreed(dto.getBreed());
        }
        if(dto.getBirthday()!=null){
            entity.setBirthday(dto.getBirthday());
        }
        if(dto.getGender()!=null){
            entity.setGender(dto.getGender());
        }
        if(dto.getWeight()!=null){
            entity.setWeight(dto.getWeight());
        }
        if(dto.getPhoto()!=null){
            entity.setPhoto(dto.getPhoto());
        }
        petJpaRepository.save(entity);
    }

    public void remove(Long id){
        petJpaRepository.deleteById(id);
    }
}
