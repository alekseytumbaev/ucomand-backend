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
                new Tag(null, "DEFAULT", TagType.PROFESSION),
                new Tag(null, "Администратор", TagType.PROFESSION),
                new Tag(null, "Разработчик", TagType.PROFESSION),
                new Tag(null, "Тестировщик", TagType.PROFESSION),
                new Tag(null, "Аналитик", TagType.PROFESSION),
                new Tag(null, "Менеджер", TagType.PROFESSION),
                new Tag(null, "Дизайнер", TagType.PROFESSION)
        );

        var skillTags = List.of(
                new Tag(null, "Java", TagType.SKILL),
                new Tag(null, "JavaScript", TagType.SKILL),
                new Tag(null, "Flutter", TagType.SKILL),
                new Tag(null, "Figma", TagType.SKILL),
                new Tag(null, "Intellij Idea", TagType.SKILL),
                new Tag(null, "Git", TagType.SKILL)
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
                null,
                null,
                null,
                UserRole.ROOT
        );
        userRepo.save(root);
    }
}
