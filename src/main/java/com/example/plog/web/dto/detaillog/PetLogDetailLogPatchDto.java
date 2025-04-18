package com.example.plog.web.dto.detaillog;

import java.time.LocalDateTime;

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
    private LocalDateTime   oldLogTime;

    private Type        newType;
    private LocalDateTime   newLogTime;
    private Mealtype    mealType;
    private String      place;
    private Integer     price;
    private Integer     takeTime;
    private String      memo;
}
