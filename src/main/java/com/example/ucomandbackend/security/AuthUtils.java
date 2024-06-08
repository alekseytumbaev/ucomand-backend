package com.example.ucomandbackend.security;

import com.example.ucomandbackend.error_handling.common_exception.CorruptedTokenException;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthUtils {

    public long extractUserIdFromJwt() throws CorruptedTokenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idStr = authentication.getName();
        try {
            return Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new CorruptedTokenException(String.format("Некорректный id='%s' в токене", idStr));
        }
    }
}
