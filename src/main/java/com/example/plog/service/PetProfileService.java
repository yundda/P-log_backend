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

        entity.setName(dto.getName());
        entity.setSpecies(dto.getSpecies());
        entity.setBreed(dto.getBreed());
        entity.setBirthday(dto.getBirthday());
        entity.setGender(dto.getGender());
        entity.setWeight(dto.getWeight());
        entity.setPhoto(dto.getPhoto());

        petJpaRepository.save(entity);
    }

    public void remove(Long id){
        petJpaRepository.deleteById(id);
    }
}
