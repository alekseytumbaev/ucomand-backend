package com.example.ucomandbackend.ad.dto;

import com.example.ucomandbackend.ad.AdType;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.dto.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdFilterDto {
    private Long userId;
    private Set<Gender> genders;
    private Integer ageFrom;
    private Integer ageTo;
    private Set<String> cities;
    private Set<TagDto> tags;
    private Set<VisibilityLevel> visibilities;
    private OffsetDateTime creationDateFrom;
    private OffsetDateTime creationDateTo;

    @JsonIgnore
    public AdType getAdType() {
        throw new NotImplementedException("Тип объявления не задан");
    }
}
