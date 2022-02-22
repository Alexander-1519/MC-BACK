package com.ryhnik.mapper;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.user.UserInputCreateDto;
import com.ryhnik.dto.user.UserOutputDto;
import com.ryhnik.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role.id")
    @Mapping(target = "masterId", source = "master.id")
    @Mapping(target = "master", source = "isMaster")
    UserOutputDto toUserOutputDto(User user);

    User toUser(UserInputCreateDto dto);

    PageDto<UserOutputDto> toPagedUserOutputDto(Page<User> users, Pageable pageable);
}
