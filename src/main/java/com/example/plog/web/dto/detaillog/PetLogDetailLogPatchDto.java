package com.example.plog.web.dto.detaillog;

import java.time.LocalTime;

import com.example.plog.repository.Enum.Mealtype;
import com.example.plog.repository.Enum.Type;

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
public class PetLogDetailLogPatchDto {
    private DetailLogDto detailLog;
    private String      petName;
    private Type        oldType;
    private LocalTime   oldLogTime;

    private Type        newType;
    private LocalTime   newLogTime;
    private Mealtype    mealType;
    private String      place;
    private Integer     price;
    private Integer     takeTime;
    private String      memo;
}
