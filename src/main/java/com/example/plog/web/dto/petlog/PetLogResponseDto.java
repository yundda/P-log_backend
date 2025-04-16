package com.example.plog.web.dto.petlog;

import com.example.plog.repository.Enum.Type;
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
public class PetLogResponseDto {
    private Long id;
    private Long petId;
    private Long userId;
    private Type type;
}
