package com.example.ucomandbackend.user.dto;

import com.example.ucomandbackend.util.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Либо email, либо phone должны быть указаны")
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

    @Schema(description = "Уникальное. Нельзя поменять после создания пользователя")
    @NotBlank(groups = OnCreate.class)
    private String telegram;

    @Schema(description = "Уникальное", example = "example@example.com")
    @Email
    private String email;

    @Schema(description = "Уникальное. E.164 номер телефона.")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Номер телефона должен содержать только код страны и цифры")
    private String phone;

    @Schema(description = "При обновлении указывать пароль не обязательно. " +
            "Пароль должен содержать от 8 до 32 символов, " +
            "как минимум одну английскую букву, одну цифру и один специальный символ")
    @Size(min = 8, max = 32)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?^&])[A-Za-z\\d@$!%*#?^&]{3,}$",
            message = "Пароль должен содержать от 8 до 32 символов, " +
                    "как минимум одну английскую букву, одну цифру и один специальный символ")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserRole role;
}
