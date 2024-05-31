package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.resume.dto.ResumeDto;
import com.example.ucomandbackend.tags.TagMapper;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserMapper;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ResumeMapper {

    public ResumeDto toResumeDto(Resume resume, List<ResumeCompetenceLevelTag> resumeLevelTags) {

        return new ResumeDto(
                resume.getId(),
                UserMapper.toUserDtoWithoutPassword(resume.getUser()),
                resume.getMotivation(),
                resume.getHoursPerWeek(),
                resume.getFreeLink(),
                resume.getOwnLink(),
                resume.getDetails(),
                resume.getCreationDate(),
                resumeLevelTags.stream().map(TagMapper::toTagDto).collect(Collectors.toSet())
        );
    }

    public Resume toResume(ResumeDto resumeDto, User user, Set<ResumeCompetenceLevelTag> resumeLevelTags) {
        return new Resume(
                resumeDto.getId(),
                user,
                resumeDto.getMotivation(),
                resumeDto.getHoursPerWeek(),
                resumeDto.getFreeLink(),
                resumeDto.getOwnLink(),
                resumeDto.getDetails(),
                resumeDto.getCreationDate(),
                resumeLevelTags
        );
    }
}
