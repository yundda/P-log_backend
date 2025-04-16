package com.example.plog.web.dto.healthlog;

import java.time.LocalTime;

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
public class HealthLogDto {
    private String vaccination;
    @JsonProperty("vaccination_log")
    private Boolean vaccinationLog;
    private String hospital;
    @Builder.Default
    private LocalTime hospitalLog = LocalTime.now();
}
