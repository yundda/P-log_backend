package com.example.plog.web.dto.healthlog;

import java.time.LocalTime;

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
public class HealthLogPatchDto {
    private String petName;
    private String vaccination;
    private Boolean vaccinationLog;
    private String hospital;
    private LocalTime hospitalLog;
    private LocalTime oldhospitalLog;
}
