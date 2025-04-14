package com.example.plog.web.dto.detaillog;

import com.example.plog.web.dto.petlog.PetLogDto;

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
public class PetLogDetailLogDto {
    private PetLogDto petlog;
    private DetailLogDto detailLog;
}
