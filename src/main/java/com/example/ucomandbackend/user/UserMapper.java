package com.example.ucomandbackend.user;

import com.example.ucomandbackend.user.dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDto toUserDtoWithoutTelegram(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                user.getAge(),
                user.getFreeLink(),
                user.getOwnLink(),
                user.getAboutMe(),
                user.getDateOfRegistration(),
                user.getCityOfResidence(),
                null,
                user.getRole()
        );
    }

    public User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getGender(),
                userDto.getAge(),
                userDto.getFreeLink(),
                userDto.getOwnLink(),
                userDto.getAboutMe(),
                userDto.getDateOfRegistration(),
                userDto.getCityOfResidence(),
                userDto.getTelegram(),
                userDto.getRole()
        );
    }
}
