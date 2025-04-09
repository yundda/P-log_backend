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
    // private Long id;
    private String name;
    private String species;
    private String breed;
    private LocalDate birthday;
    private Gender gender; 
    private Double weight;
    private String photo;
    private Long ownerId;
}