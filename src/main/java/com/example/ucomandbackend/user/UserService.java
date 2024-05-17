package com.example.ucomandbackend.user;

import com.example.ucomandbackend.error_handling.NotFoundException;
import com.example.ucomandbackend.security.TokenDto;
import com.example.ucomandbackend.security.TokenService;
import com.example.ucomandbackend.user.dto.CredentialsDto;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.user.exception.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserRepository userRepo;

    /**
     * @throws NotFoundException
     * @throws WrongPasswordException
     */
    @Transactional(readOnly = true)
    public TokenDto signInUser(CredentialsDto credentialsDto) {
        Optional<User> userOpt = findUserByCredentials(credentialsDto);

        if (userOpt.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }

        if (!passwordEncoder.matches(credentialsDto.getPassword(), userOpt.get().getPassword())) {
            throw new WrongPasswordException("Неверный пароль");
        }

        return tokenService.generateToken(userOpt.get());
    }

    /**
     * @throws org.springframework.dao.DataIntegrityViolationException какое-то уникальное поле уже существует
     */
    @Transactional
    public TokenDto signUpUser(UserDto userDto) {
        return signUpUserWithRole(userDto, UserRole.USER);
    }

    private TokenDto signUpUserWithRole(UserDto userDto, UserRole role) {
        userDto.setId(null);
        User newUser = UserMapper.toUser(
                userDto,
                passwordEncoder.encode(userDto.getPassword()),
                role
        );

        User savedUser = userRepo.save(newUser);
        return tokenService.generateToken(savedUser);
    }

    private Optional<User> findUserByCredentials(CredentialsDto credentialsDto) {
        if (credentialsDto.getTelegram() != null) {
            return userRepo.findByTelegram(credentialsDto.getTelegram());
        }
        if (credentialsDto.getEmail() != null) {
            return userRepo.findByEmail(credentialsDto.getEmail());
        }
        return userRepo.findByPhone(credentialsDto.getPhone());

    }
}
