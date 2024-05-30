package com.example.ucomandbackend.user;

import com.example.ucomandbackend.error_handling.common_exception.NotFoundException;
import com.example.ucomandbackend.security.TokenDto;
import com.example.ucomandbackend.security.TokenService;
import com.example.ucomandbackend.tags.TagService;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.dto.CredentialsDto;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.user.dto.UserRole;
import com.example.ucomandbackend.user.exception.WrongPasswordException;
import com.example.ucomandbackend.util.AuthUtils;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserRepository userRepo;
    private final TagService tagService;

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
        userDto.setDateOfRegistration(OffsetDateTime.now());
        var tags = userDto.getTags() == null ?
                Set.of(tagService.getTagByName("DEFAULT")) :
                new HashSet<>(tagService.getAllTagsByNames(userDto.getTags().stream().map(TagDto::getName).toList()));

        User newUser = UserMapper.toUser(
                userDto,
                tags,
                passwordEncoder.encode(userDto.getPassword()),
                role
        );

        User savedUser = userRepo.save(newUser);
        return tokenService.generateToken(savedUser);
    }

    /**
     * @throws NotFoundException пользователь не найден
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
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

    /**
     * @throws NotFoundException пользователь не найден
     */
    @Transactional(readOnly = true)
    public UserDto getCurrentUser() {
        long userId = AuthUtils.extractUserIdFromJwt();
        return UserMapper.toUserDtoWithoutPassword(getUserById(userId));
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(PageableDto pageableDto) {
        return userRepo.findAll(PageableMapper.toPageable(pageableDto))
                .stream()
                .map(UserMapper::toUserDtoWithoutPassword).toList();
    }

    @Transactional
    public void deleteUserById(Long userId) {
        userRepo.deleteById(userId);
    }
}
