package com.example.plog.web.dto.healthlog;

import java.time.LocalTime;

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
public class HealthLogResponseDto {
    private String vaccination;
    private Boolean vaccination_log;
    private String hospital;
    private LocalTime hospital_log;
}
