package com.example.ucomandbackend.project.dto;

import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.vacancy.VacancyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private ProjectStage projectStage;

    @NotBlank
    @Schema(description = "Сфера деятельности")
    private String fieldOfWork;

    @NotBlank
    private String description;

    private String freeLink;

    private String ownLink;

    @NotBlank
    @Schema(description = "Что уже сделано?")
    private String whatAlreadyDone;

    @NotBlank
    private String goals;

    @Valid
    private List<UserDto> command;

    private String projectNews;

    @Valid
    private List<VacancyDto> vacancies;

    private OffsetDateTime creationDate;
}
