package com.example.plog.web.dto.pet;
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
public class PetCreateDto {
    private String petName;
    private String petSpecies;
    private String petBreed;
    private String petBirthday;
    private Gender petGender;
    private Double petWeight;
    private String petPhoto;

}
