package com.example.ucomandbackend.util;

import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserRepository;
import com.example.ucomandbackend.user.UserRole;
import com.example.ucomandbackend.user.dto.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;

    @Override
    @Transactional
    public void run(String... args) {
        addRoot();
    }


    private void addRoot() {
        if (userRepo.existsByTelegram("@rootadmin")) {
            return;
        }

        var root = new User(
                1L,
                "root",
                Gender.MALE,
                21,
                null,
                "@rootadmin",
                null,
                null,
                passwordEncoder.encode("Root1234!"),
                UserRole.ROOT
        );
        userRepo.save(root);
    }
}
