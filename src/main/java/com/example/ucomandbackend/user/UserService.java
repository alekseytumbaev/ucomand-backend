package com.example.ucomandbackend.user;

import com.example.ucomandbackend.city.City;
import com.example.ucomandbackend.city.CityService;
import com.example.ucomandbackend.error_handling.common_exception.InternalErrorException;
import com.example.ucomandbackend.error_handling.common_exception.NotFoundException;
import com.example.ucomandbackend.security.AuthUtils;
import com.example.ucomandbackend.security.TokenDto;
import com.example.ucomandbackend.security.TokenService;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.user.dto.UserRole;
import com.example.ucomandbackend.user.exception.TelegramAuthException;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${telegram.bot.token}")
    private String botToken;

    private final UserRepository userRepo;
    private final CityService cityService;
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
     * @throws NotFoundException пользователь или город не найден
     */
    @Transactional
    public UserDto updateCurrentUser(UserDto userDto) {
        long userId = AuthUtils.extractUserIdFromJwt();
        User user = getUserById(userId);
        userDto.setId(user.getId());
        userDto.setTelegram(user.getTelegram());
        userDto.setRole(user.getRole());
        var city = cityService.getCityById(userDto.getCityOfResidence().getId());
        return UserMapper.toUserDtoWithoutTelegram(userRepo.save(UserMapper.toUser(userDto, city)));
    }

    public TokenDto authUserByTelegram(Map<String, Object> telegramData) {
        Optional<User> userOpt = userRepo.findByTelegram((String) telegramData.get("username"));
        if (userOpt.isPresent()) {
            return tokenService.generateToken(userOpt.get());
        }
        if (!telegramDataIsValid(telegramData)) {
            throw new TelegramAuthException("Не удалось войти через телеграм");
        }
        User user = userRepo.save(UserMapper.toUser(telegramData, OffsetDateTime.now()));
        return tokenService.generateToken(user);
    }

    //TODO проверить
    private boolean telegramDataIsValid(Map<String, Object> telegramData) {
        String hash = (String) telegramData.get("hash");
        telegramData.remove("hash");

        String dataCheckString = telegramData.entrySet().stream()
                .map(kvp -> kvp.getKey() + "=" + kvp.getValue())
                .sorted()
                .collect(Collectors.joining("\n"));

        try {
            SecretKeySpec sk = new SecretKeySpec(
                    MessageDigest.getInstance("SHA-256").digest(botToken.getBytes(UTF_8)), "SHA256");

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(sk);

            String dataCheckHash = HexFormat.of().formatHex(mac.doFinal(dataCheckString.getBytes(UTF_8)));
            return hash.equals(dataCheckHash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new InternalErrorException("Не удалось войти через телеграм", e);
        }
    }

    /**
     * @throws NotFoundException город не найден
     */
    public TokenDto signup(UserDto userDto) {
        userDto.setId(null);
        userDto.setRole(UserRole.ADMIN);
        City city = null;
        if (userDto.getCityOfResidence() != null) {
            city = cityService.getCityById(userDto.getCityOfResidence().getId());
        }
        User user = userRepo.save(UserMapper.toUser(userDto, city));
        return tokenService.generateToken(user);
    }

    /**
     * @throws NotFoundException пользователь не найден
     */
    public TokenDto signin(String telegram) {
        User user = userRepo.findByTelegram(telegram).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return tokenService.generateToken(user);
    }
}
