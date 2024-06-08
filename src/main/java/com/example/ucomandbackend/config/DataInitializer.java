package com.example.ucomandbackend.config;

import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.tags.TagRepository;
import com.example.ucomandbackend.tags.dto.TagType;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserRepository;
import com.example.ucomandbackend.user.dto.Gender;
import com.example.ucomandbackend.user.dto.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final TagRepository tagRepo;

    //TODO придумать как нормально иницииализировать данные
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
                new Tag(1L, "Администратор", TagType.PROFESSION),
                new Tag(2L, "Разработчик", TagType.PROFESSION),
                new Tag(3L, "Тестировщик", TagType.PROFESSION),
                new Tag(4L, "Аналитик", TagType.PROFESSION),
                new Tag(5L, "Менеджер", TagType.PROFESSION),
                new Tag(6L, "Дизайнер", TagType.PROFESSION)
        );

        var skillTags = List.of(
                new Tag(7L, "Java", TagType.SKILL),
                new Tag(8L, "JavaScript", TagType.SKILL),
                new Tag(9L, "Flutter", TagType.SKILL),
                new Tag(10L, "Figma", TagType.SKILL),
                new Tag(11L, "Intellij Idea", TagType.SKILL),
                new Tag(12L, "Git", TagType.SKILL)
        );

        tagRepo.saveAll(professionTags);
        tagRepo.saveAll(skillTags);
    }


    private void addRoot() {
        if (userRepo.existsByTelegram("rootadmin")) {
            return;
        }

        var root = new User(
                1L,
                "Рут",
                "Рут",
                Gender.MALE,
                21,
                null,
                null,
                null,
                OffsetDateTime.now(),
                null,
                "rootadmin", //TODO поместить в пропертис
                UserRole.ROOT
        );
        userRepo.save(root);
    }
}
