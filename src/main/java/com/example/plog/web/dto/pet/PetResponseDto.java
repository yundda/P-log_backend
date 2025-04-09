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
    private Long petId;
    private String petName;
    private String petSpecies;
    private String petBreed;
    private Gender petGender;
    private LocalDate petBirthday;
    private Double petWeight;
    private String petImageUrl;
    // private List<PetProfileDto> petProfileList; // 반려동물 프로필 리스트

    // 추가 필드들
    // private Long ownerId; // 반려동물 소유자 ID
    // private Long familyId; // 반려동물 가족 ID
    // private String userNickname; // 반려동물 소유자 닉네임
    
}
