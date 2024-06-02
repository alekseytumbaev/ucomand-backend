package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.resume.Resume;
import com.example.ucomandbackend.resume.ResumeCompetenceLevelTag;
import com.example.ucomandbackend.tags.dto.CompetenceLevel;
import com.example.ucomandbackend.tags.dto.TagDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagMapper {

    public TagDto toTagDto(Tag tag, CompetenceLevel competenceLevel) {
        return new TagDto(
                tag.getId(),
                tag.getName(),
                competenceLevel,
                tag.getType()
        );
    }

    public TagDto toTagDto(Tag tag) {
        return toTagDto(tag, null);
    }

    public Tag toTag(TagDto tag) {
        return new Tag(
                tag.getId(),
                tag.getName(),
                tag.getType()
        );
    }

    public ResumeCompetenceLevelTag toResumeCompetenceLevelTag(Long id,
                                                               Tag tag,
                                                               Resume resume,
                                                               CompetenceLevel competenceLevel) {
        return new ResumeCompetenceLevelTag(
                id,
                competenceLevel,
                resume,
                tag
        );
    }
}
