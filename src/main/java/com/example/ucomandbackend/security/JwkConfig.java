package com.example.ucomandbackend.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

@Configuration
@Slf4j
public class JwkConfig {

    private static final String privateKey =
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC628Gwa0P2eEARsbRiU9IDMgS3jPNh1YHwzuKzjhtKnnVtgMQxJTPKpd/i8p4gbp01jQpic429k87LeSzShrCC3Wb80MRt7cV1MiQgQSxplyq9S+09qKPkfypkaG8hsF4BizyxfMobbkTKPy1W8wen7doOIrqUT5gXj8+90Lcm0m4XEJ+8GHYfT9F/fiQRRhLnIctBjUy47OnaQGF2qdCY2CrZM6u4SoHsTRPMli9klftCbhMhME27zjaiHoXQbPiLxNAyLoHSNEqJjqjWt6JLB2RpU43w46Ll4HdJAPHaQSrenmyxnj3hbZl6Kk/SHL4ZuYRPO960PUeIyZ6zkSs9AgMBAAECggEACer5HFqNKGtYhzfl6aQmTPoeKvnlvyccdwIcIUilJTBE7lN71lnxpktolzymbPVCDeIXD4OvncTXIJClcuUoQEHtIfUYv/bzWJWZOkcLSu11nMeHZ5LoZAvl3Z93apPYYX+Y8Ji9h3gyWYPB7dAHc3/Z548rVQzmdzWwYpRN7Fb+dRIyvSMNQETIgVmPeyIzoy7K1pJ6UPc+ZXQkl6hWQfBDIgZdMmfCqeQma9QqfdWCpvjak6WUm14FlbbhVwYGVzV9hiTuDY/73Wbh7HIGB70NVCOTWzU87n/9anJeu+i/1dUbBLztJlP1vm5np8nD/32+6UTcYUXHha2N3HyhdwKBgQD/0CTBRwoZbkaZPipxHtIXtEQHrQZuitU3P+RSRhfBElEeUiJPniLA1NsL9hHUi4QMaV6eEVrzUTBdkvAzuI9llqof5C4T0LBWGkfqIDhBRFZLC4p14gb++R0WoMjAXaC/iHKAgF7IdabHA5yB3uNEwpn3TGr3vZBkYhd0BeJOZwKBgQC6/raZqHpCsF8C45lVD/zr5kJUdjghJ9UkJTr5mrMca8+lJHNooVoy0kenW0afN7aHcsNR2LxsMYjENuy+W8RN3xMzn/1l1mLXqt67OtM+AzCiWWnmNqIMaYMsFAPan85l6943o4ISAydDVDX/1VBgtDPxnTwn5rr1hCfqbVMquwKBgGmr0YMFlWZr5z3mMCIKWT7xeCizkLSZJPptqiNSUP2wU1HzG7lH2ZNOWi3WuAbUmveFj1A8WxWntBLzX8X9tz5/vkbuOxp0WmM29/3+0T4kvIDDg66ub7VjsmBmiobsJjr4YlQysvSbpQkQtNXw/HCq8m71Yjp0U/EjlAyZYaKBAoGAN9sNwChLHNCCEOLbUkVMT0qgVYpaiczUJc/ZJeXHyp2JkNKqhn5r6vJU5PCmz7hclWj6XuBWqBrDt5iQbuwKXxhbK/iGQH0hWTp4/YQ8xTE+vb/nmhSHox2BSRFRdWR2yosB/ayhla/Lcpkc6CZXtAsf2PQWm/SXs+6d3bB6PzECgYBeCav5q83eXbB4AexcR0m9u2Zdm5h9Rg6ZQ94JZpdoLAzS3FXj3ZxVyOPvKFHS8n0grh6h8iJQwH6kOLb4BBarnzKA28oZvY8DCyOaIBKG1S3Q9WzoVDoVRYrgPd8b0skiVQWSE7eQ6Av7HzR51orJolaiY3NKzxAYZFSWOrMb7Q==";

    private static final String publicKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAutvBsGtD9nhAEbG0YlPSAzIEt4zzYdWB8M7is44bSp51bYDEMSUzyqXf4vKeIG6dNY0KYnONvZPOy3ks0oawgt1m/NDEbe3FdTIkIEEsaZcqvUvtPaij5H8qZGhvIbBeAYs8sXzKG25Eyj8tVvMHp+3aDiK6lE+YF4/PvdC3JtJuFxCfvBh2H0/Rf34kEUYS5yHLQY1MuOzp2kBhdqnQmNgq2TOruEqB7E0TzJYvZJX7Qm4TITBNu842oh6F0Gz4i8TQMi6B0jRKiY6o1reiSwdkaVON8OOi5eB3SQDx2kEq3p5ssZ494W2ZeipP0hy+GbmETzvetD1HiMmes5ErPQIDAQAB";


    private final RSAKey rsaKey = generateRsa(publicKey, privateKey);

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    private static RSAKey generateRsa(String publicKey, String privateKey) {
        try {
            // Decode Base64 strings to get key bytes
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);

            // Create key specifications
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

            // Generate public and private keys
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

            // Build and return RSAKey object
            return new RSAKey.Builder(rsaPublicKey)
                    .privateKey(rsaPrivateKey)
                    .keyID(UUID.randomUUID().toString()) // Assuming UUID is imported
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate RSAKey", e);
        }
    }
}
