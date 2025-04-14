package com.example.plog.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.plog.repository.detaillog.DetaillogEntity;
import com.example.plog.web.dto.detaillog.DetailLogDto;

@Mapper(componentModel = "spring")
public interface DetailLogMapper {
    DetailLogMapper INSTANCE = Mappers.getMapper(DetailLogMapper.class);

    DetaillogEntity toEntity(DetailLogDto dto);
}
