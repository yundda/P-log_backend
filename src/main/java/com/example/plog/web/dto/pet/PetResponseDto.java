package com.example.plog.web.dto.pet;
import java.time.LocalDate;

import com.example.plog.repository.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetResponseDto {
    private String petName;
    private String petSpecies;
    private String petBreed;
    private Gender petGender;
    private LocalDate petBirthday;
    private Double petWeight;
    private String petImageUrl;
}
