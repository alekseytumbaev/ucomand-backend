package com.example.ucomandbackend.user.dto;

import com.example.ucomandbackend.profession.api.ProfessionDto;
import com.example.ucomandbackend.util.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Либо email, либо phone должны быть указаны")
public class UserDto {

    private Long id;

    private String name;


    @NotNull
    private Gender gender;

    @Min(1)
    private Integer age;

    private String link;

    @Valid
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private ProfessionDto profession;

    @Schema(description = "Уникальное")
    @NotBlank
    private String telegram;

    @Schema(description = "Уникальное", example = "example@example.com")
    @Email
    private String email;

    @Schema(description = "Уникальное. E.164 номер телефона.")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Номер телефона должен содержать только код страны и цифры")
    private String phone;

    @Schema(description = "При обновлении указывать пароль не обязательно")
    @NotBlank(groups = OnCreate.class)
    @Size(min = 8, max = 32)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?^&])[A-Za-z\\d@$!%*#?^&]{3,}$",
            message = "Пароль должен содержать от 8 до 32 символов, " +
                    "как минимум одну английскую букву, одну цифру и один специальный символ")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
