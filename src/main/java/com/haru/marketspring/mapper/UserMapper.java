package com.haru.marketspring.mapper;

import com.haru.marketspring.dto.user.UserCreateDTO;
import com.haru.marketspring.dto.user.UserResponseDTO;
import com.haru.marketspring.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDTO toUserResponseDTO(User user);

    List<UserResponseDTO> toUserResponseDTOList(List<User> users);

    @Mapping(target = "id", ignore = true)
    User toUser(UserCreateDTO userCreateDTO);
}
