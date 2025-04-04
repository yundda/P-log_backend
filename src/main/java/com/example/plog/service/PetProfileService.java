package com.example.plog.service;

import org.springframework.stereotype.Component;

import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.pet.PetRepository;
import com.example.plog.service.mapper.PetProfileMapper;
import com.example.plog.web.dto.PetProfileDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PetProfileService implements PetProfileMapper {

    private final PetRepository PetRepository;

    
    public Long register(PetProfileDto dto){
        PetEntity entity = dtoToEntity(dto);
        System.out.println(entity.getGender());
        PetEntity saved = PetRepository.save(entity);
        return saved.getId();
    }

    
    public PetProfileDto read(Long id) {
        PetEntity entity = PetRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
    return entityToDto(entity);
    }

    
    public void modify(PetProfileDto dto){
        PetEntity entity = PetRepository.findById(dto.getId())
        .orElseThrow(() -> new RuntimeException("Pet not found with id: " + dto.getId()));

        entity.setName(dto.getName());
        entity.setSpecies(dto.getSpecies());
        entity.setBreed(dto.getBreed());
        entity.setBirthday(dto.getBirthday());
        entity.setGender(dto.getGender());
        entity.setWeight(dto.getWeight());
        entity.setPhoto(dto.getPhoto());

        PetRepository.save(entity);
    }

    public void remove(Long id){
        PetRepository.deleteById(id);
    }
}
