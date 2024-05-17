package com.example.ucomandbackend.tags;

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
    @Size(min = 1, max = 32)
    @Schema(description = "Уникальное")
    private String name;

    @Schema(defaultValue = "MISC")
    private TagType type = TagType.MISC;

    @Schema(defaultValue = "VERIFICATION")
    private TagAvailabilityStatus availabilityStatus = TagAvailabilityStatus.VERIFICATION;

}
