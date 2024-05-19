package com.example.ucomandbackend.project;

import com.example.ucomandbackend.project.dto.ProjectDto;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserMapper;
import com.example.ucomandbackend.vacancy.Vacancy;
import com.example.ucomandbackend.vacancy.VacancyMapper;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class ProjectMapper {

    public Project toProject(ProjectDto projectDto, Set<User> command, Set<Vacancy> vacancies) {
        return new Project(
                projectDto.getId(),
                projectDto.getName(),
                projectDto.getProjectStage(),
                projectDto.getFieldOfWork(),
                projectDto.getDescription(),
                projectDto.getFreeLink(),
                projectDto.getOwnLink(),
                projectDto.getWhatAlreadyDone(),
                projectDto.getGoals(),
                command,
                projectDto.getProjectNews(),
                vacancies,
                projectDto.getCreationDate()
        );
    }

    public ProjectDto toProjectDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getStage(),
                project.getFieldOfWork(),
                project.getDescription(),
                project.getFreeLink(),
                project.getOwnLink(),
                project.getWhatAlreadyDone(),
                project.getWhatAlreadyDone(),
                project.getCommand().stream().map(UserMapper::toUserDtoWithoutPassword).toList(),
                project.getProjectNews(),
                project.getVacancies().stream().map(VacancyMapper::toVacancyDto).toList(),
                project.getCreationDate()
        );
    }
}
