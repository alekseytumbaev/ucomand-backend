package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.tags.dto.TagDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagMapper {

    public TagDto toTagDto(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName(),
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
}
