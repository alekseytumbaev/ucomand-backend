package com.example.ucomandbackend.config;

import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.tags.TagRepository;
import com.example.ucomandbackend.tags.dto.TagAvailabilityStatus;
import com.example.ucomandbackend.tags.dto.TagType;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserRepository;
import com.example.ucomandbackend.user.dto.Gender;
import com.example.ucomandbackend.user.dto.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final TagRepository tagRepo;

    @Override
    @Transactional
    public void run(String... args) {
        addTags();
        addRoot();
    }

    private void addTags() {
        if (tagRepo.count() != 0) {
            return;
        }

        var professionTags = List.of(
                new Tag(null, "DEFAULT", TagType.PROFESSION, TagAvailabilityStatus.UNAVAILABLE),
                new Tag(null, "Администратор", TagType.PROFESSION, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "Разработчик", TagType.PROFESSION, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "Тестировщик", TagType.PROFESSION, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "Аналитик", TagType.PROFESSION, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "Менеджер", TagType.PROFESSION, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "Дизайнер", TagType.PROFESSION, TagAvailabilityStatus.AVAILABLE)
        );

        var skillTags = List.of(
                new Tag(null, "Java", TagType.SKILL, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "JavaScript", TagType.SKILL, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "Flutter", TagType.SKILL, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "Figma", TagType.SKILL, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "Intellij Idea", TagType.SKILL, TagAvailabilityStatus.AVAILABLE),
                new Tag(null, "Git", TagType.SKILL, TagAvailabilityStatus.AVAILABLE)
        );

        tagRepo.saveAll(professionTags);
        tagRepo.saveAll(skillTags);
    }


    private void addRoot() {
        if (userRepo.existsByTelegram("@rootadmin")) {
            return;
        }

        var root = new User(
                1L,
                "root",
                null,
                Set.of(tagRepo.getByName("Администратор").get()),
                Gender.MALE,
                21,
                null,
                null,
                null,
                OffsetDateTime.now(),
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
