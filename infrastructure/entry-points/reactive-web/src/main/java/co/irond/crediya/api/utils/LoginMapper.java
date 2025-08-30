package co.irond.crediya.api.utils;

import co.irond.crediya.api.dto.LoginRequestDto;
import co.irond.crediya.model.dto.LoginDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    @Mapping(source = "requestDto.email", target = "email")
    LoginDto toLoginDto(LoginRequestDto requestDto);

    LoginRequestDto toLoginRequestDto(LoginDto loginDto);

}
