package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.tags.TagMapper;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserMapper;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ResumeMapper {

    public ResumeDto toResumeDto(Resume resume) {
        return new ResumeDto(
                resume.getId(),
                UserMapper.toUserDtoWithoutPassword(resume.getUser()),
                resume.getMotivation(),
                resume.getHoursPerWeek(),
                resume.getFreeLink(),
                resume.getOwnLink(),
                resume.getDetails(),
                resume.getTags().stream().map(TagMapper::toTagDto).collect(Collectors.toSet())
        );
    }

    public Resume toResume(ResumeDto resumeDto, User user, Set<Tag> tags) {
        return new Resume(
                resumeDto.getId(),
                user,
                resumeDto.getMotivation(),
                resumeDto.getHoursPerWeek(),
                resumeDto.getFreeLink(),
                resumeDto.getOwnLink(),
                resumeDto.getDetails(),
                tags
        );
    }
}
