package com.example.plog.web.dto.healthlog;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private Long log_id;
    private String vaccination;
    @JsonProperty("vaccination_log")
    private Boolean vaccination_log;
    private String hospital;
    private LocalDateTime hospital_log;
}
