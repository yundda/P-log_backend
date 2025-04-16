package com.example.plog.web.dto.pet;

import java.time.LocalDate;

import com.example.plog.repository.Enum.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetUpdateDto {
    private String name;
    private String petName;
    private String petSpecies;
    private String petBreed;
    private LocalDate petBirthday;
    private Gender petGender; 
    private Double petWeight;
    private String petPhoto;
}
