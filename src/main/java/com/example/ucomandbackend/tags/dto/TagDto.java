package com.example.ucomandbackend.tags.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagDto {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 64)
    @Schema(description = "Уникальное", example = "Java")
    private String name;

    @Min(1)
    @Max(3)
    @Schema(description = "Уровень компетенции: 1 - junior, 2 - middle, 3 - senior", example = "1")
    private Integer competenceLevel;

    private TagType type;
}
