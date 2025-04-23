package com.example.plog.web.dto.healthlog;

import com.example.plog.web.dto.petlog.PetLogDtoForHealth;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PetLogHealthLogDto {
    @JsonProperty("petLog")
    private PetLogDtoForHealth petlog;

    @JsonProperty("healthLog")
    private HealthLogDto healthLog;

    @JsonCreator
    public PetLogHealthLogDto(
        @JsonProperty("petLog") PetLogDtoForHealth petlog,
        @JsonProperty("healthLog") HealthLogDto healthLog
    ){
        this.petlog = petlog;
        this.healthLog = healthLog;
    }
}
