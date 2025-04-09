package com.example.plog.web.dto.pet;

import com.example.plog.repository.Enum.Role;
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
public class PetProfileListDto {
    private Long petId;
    private String petName;
    private String petPhoto;
    private Role role;
}
