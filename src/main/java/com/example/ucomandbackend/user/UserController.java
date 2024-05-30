package com.example.ucomandbackend.user;

import com.example.ucomandbackend.security.TokenDto;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.util.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.Min;
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

    @GetMapping("/currentUser")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PutMapping("/currentUser")
    public UserDto updateCurrentUser(@RequestBody @Validated UserDto userDto) {
        return userService.updateCurrentUser(userDto);
    }

    //TODO только для админов
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

    //TODO удалить
    @Operation(description = "Получить токен рут-админа")
    @SecurityRequirements
    @GetMapping("/root/token")
    public TokenDto getRootToken() {
        return userService.getRootToken();
    }
}
