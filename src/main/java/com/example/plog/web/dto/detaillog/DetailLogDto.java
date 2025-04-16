package com.example.plog.web.dto.detaillog;

import java.time.LocalTime;

import com.example.plog.repository.Enum.Mealtype;
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
public class DetailLogDto {
    @Builder.Default
    private LocalTime logTime = LocalTime.now();
    private Mealtype mealType;
    private String place;
    private Integer price;
    private Integer takeTime;
    private String memo;
}
