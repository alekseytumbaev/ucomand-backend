package com.example.ucomandbackend.user;

import com.example.ucomandbackend.city.City;
import com.example.ucomandbackend.city.CityMapper;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.user.dto.UserRole;
import lombok.experimental.UtilityClass;

import java.time.OffsetDateTime;
import java.util.Map;

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
                CityMapper.toDto(user.getCityOfResidence()),
                null,
                user.getRole()
        );
    }

    public User toUser(UserDto userDto, City city) {
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
                city,
                userDto.getTelegram(),
                userDto.getRole()
        );
    }

    public User toUser(Map<String, Object> telegramData, OffsetDateTime dateOfRegistration) {
        return new User(
                null,
                (String) telegramData.get("first_name"),
                (String) telegramData.get("last_name"),
                null,
                null,
                null,
                null,
                null,
                dateOfRegistration,
                null,
                (String) telegramData.get("username"),
                UserRole.USER
        );
    }
}
