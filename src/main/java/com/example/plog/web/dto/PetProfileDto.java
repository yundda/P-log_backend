package com.example.plog.web.dto;

import java.time.LocalDate;

import com.example.plog.repository.Enum.Gender;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PetProfileDto {
    private String petName;
    private String petSpecies;
    private String petBreed;
    private LocalDate petBirthday;
    private Gender petGender; 
    private Double petWeight;
    private String petPhoto;
    private Long ownerId;
    private String email;
    private String password;
    private String nickname;
    private Long requestId;
}