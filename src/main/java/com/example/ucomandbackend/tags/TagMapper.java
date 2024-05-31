package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.resume.Resume;
import com.example.ucomandbackend.resume.ResumeCompetenceLevelTag;
import com.example.ucomandbackend.tags.dto.CompetenceLevel;
import com.example.ucomandbackend.tags.dto.TagDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagMapper {

    public TagDto toTagDto(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName(),
                null,
                tag.getType(),
                tag.getAvailabilityStatus()
        );
    }

    public Tag toTag(TagDto tag) {
        return new Tag(tag.getId(),
                tag.getName(),
                tag.getType(),
                tag.getAvailabilityStatus());
    }

    public ResumeCompetenceLevelTag toResumeCompetenceLevelTag(Long id, Tag tag, Resume resume, CompetenceLevel competenceLevel) {
        return new ResumeCompetenceLevelTag(
                id,
                competenceLevel,
                resume,
                tag
        );
    }

    public TagDto toTagDto(ResumeCompetenceLevelTag resumeCompetenceLevelTag) {
        return new TagDto(
                resumeCompetenceLevelTag.getTag().getId(),
                resumeCompetenceLevelTag.getTag().getName(),
                resumeCompetenceLevelTag.getCompetenceLevel(),
                resumeCompetenceLevelTag.getTag().getType(),
                resumeCompetenceLevelTag.getTag().getAvailabilityStatus()
        );
    }
}
