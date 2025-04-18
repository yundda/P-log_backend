package com.example.plog.web.dto.detaillog;

import java.time.LocalTime;

import com.example.plog.repository.Enum.Type;
import com.example.plog.web.dto.petlog.PetLogDto;
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
public class PetLogDetailLogDto {

    @JsonProperty("petLog")
    private PetLogDto petlog;

    @JsonProperty("detailLog")
    private DetailLogDto detailLog;

    @JsonCreator
    public PetLogDetailLogDto(
        @JsonProperty("petLog") PetLogDto petlog,
        @JsonProperty("detailLog") DetailLogDto detailLog) {
        this.petlog = petlog;
        this.detailLog = detailLog;  
    }
}
