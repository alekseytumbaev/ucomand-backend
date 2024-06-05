package com.example.ucomandbackend.resume.dto;

import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {

    private Long id;

    @Valid
    private UserDto userDto;

    @Valid
    private TagDto profession;

    @Valid
    @NotNull
    private Set<TagDto> skills;

    @NotNull
    private MotivationType motivation;

    @Min(1)
    @NotNull
    private int hoursPerWeek;

    private String freeLink;

    private String ownLink;

    @Schema(description = "Свободное поле")
    private String details;

    private VisibilityLevel visibility;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime creationDate;
}
