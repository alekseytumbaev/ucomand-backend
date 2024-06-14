package com.example.ucomandbackend.user.dto;

import com.example.ucomandbackend.util.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(innerTypeName = "F")
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Gender gender;

    @Min(1)
    private Integer age;

    private String freeLink;

    private String ownLink;

    private String aboutMe;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dateOfRegistration;

    private String cityOfResidence;

    @Schema(description = "Уникальное. Нельзя поменять и посомтреть после создания пользователя")
    @NotBlank(groups = OnCreate.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String telegram;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserRole role;
}
