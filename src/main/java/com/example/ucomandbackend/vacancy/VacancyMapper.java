package com.example.ucomandbackend.vacancy;

import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.tags.TagMapper;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserMapper;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class VacancyMapper {

    public Vacancy toVacancy(VacancyDto vacancyDto, User user, Set<Tag> tags) {
        return new Vacancy(
                vacancyDto.getId(),
                user,
                tags,
                vacancyDto.getDescription(),
                vacancyDto.getPayment(),
                vacancyDto.getCreationDate()
        );
    }

    public VacancyDto toVacancyDto(Vacancy vacancy) {
        return new VacancyDto(
                vacancy.getId(),
                UserMapper.toUserDtoWithoutPassword(vacancy.getOwner()),
                vacancy.getTags().stream().map(TagMapper::toTagDto).toList(),
                vacancy.getDescription(),
                vacancy.getPayment(),
                vacancy.getCreationDate()
        );
    }
}
