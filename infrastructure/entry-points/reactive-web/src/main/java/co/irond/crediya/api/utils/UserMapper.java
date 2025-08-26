package co.irond.crediya.api.utils;

import co.irond.crediya.api.dto.UserRegistrationDto;
import co.irond.crediya.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userDto.name", target = "name")
    User toUser(UserRegistrationDto userDto);

    UserRegistrationDto toUserDto(User user);
}