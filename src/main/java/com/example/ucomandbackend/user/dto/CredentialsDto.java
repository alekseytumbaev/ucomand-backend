package com.example.ucomandbackend.user.dto;

import com.example.ucomandbackend.util.OneOf;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@OneOf(value = {"telegram", "email", "phone"}, message = "Необходимо указать логин, почту или телефон")
@Schema(description = "Либо email, либо phone, либо telegram должны быть указаны")
public class CredentialsDto {

    @Schema(example = "@rootadmin")
    private String telegram;

    @Email
    @Schema(example = "example@example.com")
    private String email;

    @Schema(description = "E.164 номер телефона")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Номер телефона должен содержать только код страны и цифры")
    private String phone;

    @NotBlank
    @Schema(example = "Root1234!")
    @Size(min = 8, max = 32)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?^&])[A-Za-z\\d@$!%*#?^&]{3,}$",
            message = "Пароль должен содержать от 8 до 32 символов, " +
                    "как минимум одну английскую букву, одну цифру и один специальный символ")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}