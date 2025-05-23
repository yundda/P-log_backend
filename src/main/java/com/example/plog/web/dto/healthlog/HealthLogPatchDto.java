package com.example.plog.web.dto.healthlog;

import java.time.LocalDateTime;

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
    private Long log_id;
    private String vaccination;
    private Boolean vaccinationLog;
    private String hospital;
    private LocalDateTime hospitalLog;
}
