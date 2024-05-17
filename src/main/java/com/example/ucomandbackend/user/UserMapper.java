package com.example.ucomandbackend.user;

import com.example.ucomandbackend.user.dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDto toUserDtoWithoutPassword(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                null,
                user.getGender(),
                user.getAge(),
                user.getLink(),
                user.getTelegram(),
                user.getEmail(),
                user.getPhone(),
                null
        );
    }

    public User toUser(UserDto userDto, String password, UserRole role) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getGender(),
                userDto.getAge(),
                userDto.getLink(),
                userDto.getTelegram(),
                userDto.getPhone(),
                userDto.getEmail(),
                password,
                role
        );
    }
}
