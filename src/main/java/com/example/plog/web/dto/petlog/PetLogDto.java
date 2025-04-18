package com.example.plog.web.dto.petlog;

import java.time.LocalDateTime;

import com.example.plog.repository.Enum.Mealtype;
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
public class PetLogDto {
    private String name;
    private Long petId;
    private Long userId;
    private Type type;
    private Long logId;
    private LocalDateTime logTime;
    private LocalDateTime logDate;
    private Mealtype mealType;
    private String place;
    private Integer price;
    private Integer takeTime;
    private String memo;
}
