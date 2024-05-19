package com.example.ucomandbackend.vacancy;

import com.example.ucomandbackend.project.Project;
import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.tags.TagMapper;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserMapper;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class VacancyMapper {

    public Vacancy toVacancy(VacancyDto vacancyDto, User user, Project project, Tag profession, Set<Tag> tags) {
        return new Vacancy(
                vacancyDto.getId(),
                user,
                project,
                profession,
                tags,
                vacancyDto.getDescription(),
                vacancyDto.getPayment(),
                vacancyDto.getCreationDate()
        );
    }

    public VacancyDto toVacancyDto(Vacancy vacancy) {
        return new VacancyDto(
                vacancy.getId(),
                vacancy.getProject() == null ? null : vacancy.getProject().getId(),
                UserMapper.toUserDtoWithoutPassword(vacancy.getOwner()),
                TagMapper.toTagDto(vacancy.getProfession()),
                vacancy.getTags().stream().map(TagMapper::toTagDto).toList(),
                vacancy.getDescription(),
                vacancy.getPayment(),
                vacancy.getCreationDate()
        );
    }
}