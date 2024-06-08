package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.ad.Ad;
import com.example.ucomandbackend.ad.AdCompetenceLevelTag;
import com.example.ucomandbackend.tags.dto.TagDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagMapper {

    public TagDto toTagDto(AdCompetenceLevelTag adCompetenceLevelTag) {
        var tag = adCompetenceLevelTag.getTag();
        return new TagDto(
                tag.getId(),
                tag.getName(),
                adCompetenceLevelTag.getCompetenceLevel(),
                tag.getType()
        );
    }

    public AdCompetenceLevelTag toAdCompetenceLevelTag(Long id, Tag tag, Ad ad, Integer competenceLevel) {
        return new AdCompetenceLevelTag(
                id,
                competenceLevel,
                ad,
                tag
        );
    }

    public TagDto toTagDtoWithoutCompetenceLevel(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName(),
                null,
                tag.getType()
        );
    }

    public Tag toTag(TagDto tag) {
        return new Tag(
                tag.getId(),
                tag.getName(),
                tag.getType()
        );
    }
}
