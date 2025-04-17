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
public class DetailLogResponseDto {
    private Long log_id;
    private LocalDateTime log_time;
    private Mealtype mealType;
    private String place;
    private Integer price;
    private Integer take_time;
    private String memo;
}
