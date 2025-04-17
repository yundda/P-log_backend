package com.example.plog.web.dto.detaillog;

import java.time.LocalDateTime;

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
    private LocalDateTime logTime;
    private Mealtype mealType;
    private String place;
    private Integer price;
    private Integer takeTime;
    private String memo;
}
