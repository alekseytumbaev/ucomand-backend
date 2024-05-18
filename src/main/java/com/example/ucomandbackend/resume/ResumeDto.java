package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {

    private Long id;

    @Valid
    private UserDto userDto;

    @NotNull
    private MotivationType motivation;

    @Min(1)
    @NotNull
    private int hoursPerWeek;

    private String freeLink;

    private String ownLink;

    @Schema(description = "Свободное поле")
    private String details;

    @Valid
    @NotNull
    private Set<TagDto> tags;
}
