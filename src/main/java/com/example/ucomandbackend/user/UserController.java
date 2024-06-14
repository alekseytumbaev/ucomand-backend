package com.example.ucomandbackend.user;

import com.example.ucomandbackend.security.TokenDto;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.util.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

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

    //TODO только админ и сам себя
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    @GetMapping("/auth/telegram")
    @SecurityRequirements
    @Operation(description = "Получить html виджета для авторизации через телеграм (https://core.telegram.org/widgets/login)")
    public ResponseEntity<Resource> auth() {
        Resource resource = new ClassPathResource("static/telegramAuth.html");
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=telegramAuth.html");
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @PostMapping("/auth/byTelegram")
    @SecurityRequirements
    @Operation(description = "Сюда нужно отправить данные, которые получили от телеграма")
    public TokenDto authUserByTelegram(@RequestBody
                                       @Schema(description = "Объект, который отправляет телеграм")
                                       Map<String, Object> telegramData) {
        return userService.authUserByTelegram(telegramData);
    }
}
