package com.example.ucomandbackend.user;

import com.example.ucomandbackend.error_handling.NotFoundException;
import com.example.ucomandbackend.security.TokenDto;
import com.example.ucomandbackend.user.dto.CredentialsDto;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.user.exception.WrongPasswordException;
import com.example.ucomandbackend.util.Default200Controller;
import com.example.ucomandbackend.util.OnCreate;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
@Validated
@RequiredArgsConstructor
public class UserController implements Default200Controller {

    private final UserService userService;

    @PutMapping("/signin")
    @SecurityRequirements
    @ApiResponse(responseCode = "200", description = "токен, валидный в течение 999999999 минут") //TODO
    @ApiResponse(responseCode = WrongPasswordException.CODE, description = WrongPasswordException.DESC)
    @ApiResponse(responseCode = NotFoundException.CODE, description = NotFoundException.DESC)
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    public TokenDto signInUser(@RequestBody @Validated CredentialsDto credentialsDto) {
        return userService.signInUser(credentialsDto);
    }

    @PostMapping("signup")
    @SecurityRequirements
    @ApiResponse(responseCode = "200", description = "Токен, валидный в течение 999999999 минут") //TODO
    @ApiResponse(responseCode = WrongPasswordException.CODE, description = WrongPasswordException.DESC)
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @ApiResponse(responseCode = "409", description = "Нарушена уникальность полей")
    TokenDto signUpUser(@RequestBody @Validated({OnCreate.class, Default.class}) UserDto userDto) {
        return userService.signUpUser(userDto);
    }
}
