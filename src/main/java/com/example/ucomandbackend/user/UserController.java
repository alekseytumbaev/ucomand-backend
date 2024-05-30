package com.example.ucomandbackend.user;

import com.example.ucomandbackend.error_handling.common_exception.NotFoundException;
import com.example.ucomandbackend.security.TokenDto;
import com.example.ucomandbackend.user.dto.CredentialsDto;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.user.exception.WrongPasswordException;
import com.example.ucomandbackend.util.OnCreate;
import com.example.ucomandbackend.util.PageableMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.Min;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/users")
@RestController
@Validated
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class UserController {

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

    @PostMapping("/signup")
    @SecurityRequirements
    @ApiResponse(responseCode = "200", description = "Токен, валидный в течение 999999999 минут") //TODO
    @ApiResponse(responseCode = WrongPasswordException.CODE, description = WrongPasswordException.DESC)
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @ApiResponse(responseCode = "409", description = "Нарушена уникальность полей")
    public TokenDto signUpUser(@RequestBody @Validated({OnCreate.class, Default.class}) UserDto userDto) {
        return userService.signUpUser(userDto);
    }

    @PostMapping("/signup/telegram")
    @Schema(hidden = true)
    public TokenDto signUpUserByTelegram() {
        return null;
    }

    @GetMapping("/currentUser")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping
    public Collection<UserDto> getAllUsers(
            @RequestParam(defaultValue = "0")
            @Validated @Min(0) Integer page,

            @RequestParam(defaultValue = "10")
            @Validated @Min(1) Integer size
    ) {
        return userService.getAllUsers(PageableMapper.toPageableDto(page, size));
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }
}
