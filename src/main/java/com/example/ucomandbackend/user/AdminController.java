package com.example.ucomandbackend.user;

import com.example.ucomandbackend.security.TokenDto;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.user.util.OnCreateAdmin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admins")
@RestController
@Validated
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class AdminController {

    private final UserService userService;

    //TODO удалить
    @GetMapping("/rootToken")
    @SecurityRequirements
    @Operation(description = "Получить токен рут-админа")
    public TokenDto getRootToken() {
        return userService.getRootToken();
    }

    //TODO только рут
    @PostMapping("/signup")
    @Operation(description = "telegram вводится вручную и используется как ключ для входа")
    public TokenDto signup(@RequestBody @Validated({OnCreateAdmin.class, Default.class}) UserDto user) {
        return userService.signup(user);
    }

    @PostMapping("/signin")
    public TokenDto signin(@RequestBody @NotBlank String telegram) {
        return userService.signin(telegram);
    }
}
