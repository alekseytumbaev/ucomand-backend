package com.example.ucomandbackend.profession.api;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionDto {

    private Long id;

    @NotBlank
    private String name;
}
