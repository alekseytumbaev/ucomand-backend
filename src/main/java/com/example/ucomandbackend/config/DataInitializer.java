package com.example.ucomandbackend.config;

import com.example.ucomandbackend.tag.Tag;
import com.example.ucomandbackend.tag.TagRepository;
import com.example.ucomandbackend.tag.dto.TagType;
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

        var motivationTags = List.of(
                new Tag(1L, "Без оплаты", TagType.MOTIVATION),
                new Tag(2L, "За оплату", TagType.MOTIVATION),
                new Tag(3L, "За долю", TagType.MOTIVATION),
                new Tag(4L, "Нужна практика", TagType.MOTIVATION)
        );

        var professionTags = List.of(
                new Tag(5L, "Администратор", TagType.PROFESSION),
                new Tag(6L, "Разработчик", TagType.PROFESSION),
                new Tag(7L, "Тестировщик", TagType.PROFESSION),
                new Tag(8L, "Аналитик", TagType.PROFESSION),
                new Tag(9L, "Менеджер", TagType.PROFESSION),
                new Tag(10L, "Дизайнер", TagType.PROFESSION)
        );

        var skillTags = List.of(
                new Tag(11L, "Java", TagType.SKILL),
                new Tag(12L, "JavaScript", TagType.SKILL),
                new Tag(13L, "Flutter", TagType.SKILL),
                new Tag(14L, "Figma", TagType.SKILL),
                new Tag(15L, "Intellij Idea", TagType.SKILL),
                new Tag(16L, "Git", TagType.SKILL)
        );

        tagRepo.saveAll(motivationTags);
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
