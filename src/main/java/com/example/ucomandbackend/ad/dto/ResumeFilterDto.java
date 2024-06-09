package com.example.ucomandbackend.ad.dto;

import com.example.ucomandbackend.ad.AdType;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.dto.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ResumeFilterDto extends AdFilterDto {

    public ResumeFilterDto(Long userId, Set<Gender> genders, Integer ageFrom, Integer ageTo, Set<String> cities, Set<TagDto> tags, Set<VisibilityLevel> visibilities, OffsetDateTime creationDateFrom, OffsetDateTime creationDateTo) {
        super(userId, genders, ageFrom, ageTo, cities, tags, visibilities, creationDateFrom, creationDateTo);
    }

    @Override
    public AdType getAdType() {
        return AdType.RESUME;
    }
}
