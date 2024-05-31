package com.example.ucomandbackend.security;

import com.example.ucomandbackend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String TOKEN_TYPE = "Bearer";
    public static final int TOKEN_LIFETIME_MIN = 999999999; //TODO перенести в проперти

    private final JwtEncoder encoder;

    public TokenDto generateToken(User user) {
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(TOKEN_LIFETIME_MIN, MINUTES))
                .subject(user.getId().toString())
                .claim("scope", authorities)
                .build();

        String token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new TokenDto(token, TOKEN_TYPE);
    }
}