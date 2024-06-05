package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.resume.dto.ResumeDto;
import com.example.ucomandbackend.tags.TagMapper;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserMapper;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ResumeMapper {

    public ResumeDto toResumeDto(Resume resume) {
        var profession = resume.getProfession();
        return new ResumeDto(
                resume.getId(),
                UserMapper.toUserDtoWithoutPassword(resume.getOwner()),
                profession == null ? null : TagMapper.toTagDto(profession.getTag(), profession.getCompetenceLevel()),
                resume.getSkills().stream()
                        .map(it -> TagMapper.toTagDto(it.getTag(), it.getCompetenceLevel()))
                        .collect(Collectors.toSet()),
                resume.getMotivation(),
                resume.getHoursPerWeek(),
                resume.getFreeLink(),
                resume.getOwnLink(),
                resume.getDetails(),
                resume.getVisibility(),
                resume.getCreationDate()
        );
    }

    public Resume toResume(ResumeDto resumeDto, User user, Set<ResumeCompetenceLevelTag> competenceLevelTags) {
        return new Resume(
                resumeDto.getId(),
                user,
                resumeDto.getMotivation(),
                resumeDto.getHoursPerWeek(),
                resumeDto.getFreeLink(),
                resumeDto.getOwnLink(),
                resumeDto.getDetails(),
                resumeDto.getVisibility(),
                resumeDto.getCreationDate(),
                competenceLevelTags
        );
    }
}
