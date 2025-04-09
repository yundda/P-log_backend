package com.example.plog.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.plog.repository.user.UserEntity;
import com.example.plog.web.dto.user.UserRegistrationDto;
import com.example.plog.web.dto.user.UserResponseDto;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target="id",ignore = true)
    UserEntity userRegistrationDtoToUserEntity(UserRegistrationDto userRegistrationDto);

    @Mapping(target="userId",source = "id")
    @Mapping(target="requestId",ignore = true)
    @Mapping(target="requesterNick",ignore = true)
    @Mapping(target="petName",ignore = true)
    @Mapping(target="token",ignore = true)
    UserResponseDto userEntityToUserResponseDto(UserEntity userEntity);

}

// EX)
// @Mapper
// public interface ItemMapper {
//     싱글톤 패턴으로 생성
//     ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

//     메소드
//     필요할 경우만 @Mapping 사용 같은 변수명 사용하고 특별한 요구사항 없을 경우, 굳이 필요 x
//     @Mapping(target = "spec.cpu", source = "cpu")
//     @Mapping(target = "spec.capacity", source = "capacity")
//     Item itemEntityToItem(ItemEntity itemEntity);

//     @Mapping(target = "storeSales" , ignore = true)
//     @Mapping(target = "stock" , expression = "java(0)")
//     ItemEntity itemBodytoItemEntity(Integer id, ItemBody itemBody);

// }
