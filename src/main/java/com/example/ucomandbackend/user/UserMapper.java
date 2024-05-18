package com.example.ucomandbackend.user;

import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.tags.TagMapper;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.user.dto.UserRole;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public UserDto toUserDtoWithoutPassword(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getTags().stream().map(TagMapper::toTagDto).collect(Collectors.toSet()),
                user.getGender(),
                user.getAge(),
                user.getFreeLink(),
                user.getOwnLink(),
                user.getAboutMe(),
                user.getDateOfRegistration(),
                user.getCityOfResidence(),
                user.getTelegram(),
                user.getEmail(),
                user.getPhone(),
                null,
                user.getRole()
        );
    }

    public User toUser(UserDto userDto, Set<Tag> tags, String password, UserRole role) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                tags,
                userDto.getGender(),
                userDto.getAge(),
                userDto.getFreeLink(),
                userDto.getOwnLink(),
                userDto.getAboutMe(),
                userDto.getDateOfRegistration(),
                userDto.getCityOfResidence(),
                userDto.getTelegram(),
                userDto.getPhone(),
                userDto.getEmail(),
                password,
                role
        );
    }
}
