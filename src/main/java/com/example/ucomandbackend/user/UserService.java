package com.example.ucomandbackend.user;

import com.example.ucomandbackend.error_handling.common_exception.NotFoundException;
import com.example.ucomandbackend.security.TokenDto;
import com.example.ucomandbackend.security.TokenService;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.security.AuthUtils;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final TokenService tokenService;

    /**
     * @throws NotFoundException пользователь не найден
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    /**
     * @throws NotFoundException пользователь не найден
     */
    @Transactional(readOnly = true)
    public UserDto getCurrentUser() {
        long userId = AuthUtils.extractUserIdFromJwt();
        return UserMapper.toUserDtoWithoutTelegram(getUserById(userId));
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(PageableDto pageableDto) {
        return userRepo.findAll(PageableMapper.toPageable(pageableDto))
                .stream()
                .map(UserMapper::toUserDtoWithoutTelegram).toList();
    }

    //TODO сделать проверку, что пользователя могут удалять только админы, либо он сам себя
    @Transactional
    public void deleteUserById(Long userId) {
        userRepo.deleteById(userId);
    }

    //TODO удалить
    @Transactional(readOnly = true)
    public TokenDto getRootToken() {
        User root = userRepo.findByTelegram("rootadmin")
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return tokenService.generateToken(root);
    }

    /**
     * @throws NotFoundException пользователь не найден
     */
    @Transactional
    public UserDto updateCurrentUser(UserDto userDto) {
        long userId = AuthUtils.extractUserIdFromJwt();
        User user = getUserById(userId);
        userDto.setId(user.getId());
        userDto.setTelegram(user.getTelegram());
        userDto.setRole(user.getRole());
        return UserMapper.toUserDtoWithoutTelegram(userRepo.save(UserMapper.toUser(userDto)));
    }
}
