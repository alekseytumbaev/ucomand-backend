package com.example.ucomandbackend.vacancy;

import com.example.ucomandbackend.resume.dto.MotivationType;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.dto.UserDto;
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
public class VacancyDto {
    private Long id;

    private UserDto owner;

    @Valid
    private List<TagDto> tags;

    @NotBlank
    private String details;

    @NotNull
    private MotivationType motivation;

    private OffsetDateTime creationDate;
}
