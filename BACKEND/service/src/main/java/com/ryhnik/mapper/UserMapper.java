package com.ryhnik.mapper;

import com.ryhnik.dao.user.UserOutputDto;
import com.ryhnik.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "role", source = "role.name.name")
    UserOutputDto toUserOutputDto(User user);
}
