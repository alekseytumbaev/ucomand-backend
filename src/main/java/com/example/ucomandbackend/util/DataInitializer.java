package com.example.ucomandbackend.util;

import com.example.ucomandbackend.profession.Profession;
import com.example.ucomandbackend.profession.ProfessionRepository;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserRepository;
import com.example.ucomandbackend.user.UserRole;
import com.example.ucomandbackend.user.dto.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final ProfessionRepository professionRepo;

    @Override
    @Transactional
    public void run(String... args) {
        addProfessions();
        addRoot();
    }

    private void addProfessions() {
        if (professionRepo.count() != 0) {
            return;
        }

        List<Profession> professions = List.of(
                new Profession(null, "Администратор"),
                new Profession(null, "Разработчик"),
                new Profession(null, "Менеджер"),
                new Profession(null, "Тестировщик"),
                new Profession(null, "Девопс"),
                new Profession(null, "Аналитик"),
                new Profession(null, "Бухгалтер")
        );

        professionRepo.saveAll(professions);
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
