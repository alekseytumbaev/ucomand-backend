package com.example.ucomandbackend.tags.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    //TODO потом убрать
    private CompetenceLevel competenceLevel;

    private TagType type;

    @Schema(defaultValue = "VERIFICATION")
    private TagAvailabilityStatus availabilityStatus = TagAvailabilityStatus.VERIFICATION;

}
