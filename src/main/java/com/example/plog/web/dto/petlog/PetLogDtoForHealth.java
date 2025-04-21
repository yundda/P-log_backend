package com.example.plog.web.dto.petlog;

import java.time.LocalTime;

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
public class PetLogDtoForHealth {
    private String name;
    private Long petId;
    private Long userId;
    private Type type;
    private Long logId;
    private String vaccination;
    private Boolean vaccination_log;
    private String hospital;
    private LocalTime hospital_log;
}
