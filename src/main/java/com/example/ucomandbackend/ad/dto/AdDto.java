package com.example.ucomandbackend.ad.dto;

import com.example.ucomandbackend.ad.AdType;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdDto {

    private Long id;

    @Valid
    private UserDto user;

    @Valid
    @NotNull
    private TagDto profession;

    @Valid
    @NotNull
    private Set<TagDto> skills;

    @Valid
    @NotNull
    private Set<TagDto> motivations;

    private String freeLink;

    private String ownLink;

    private String contacts;

    @Schema(description = "Свободное поле")
    private String details;

    private VisibilityLevel visibility;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime creationDate;

    @JsonIgnore
    public AdType getAdType() {
        throw new NotImplementedException("Тип объявления не задан");
    }

    @JsonIgnore
    public Set<TagDto> getTags() {
        skills.add(profession);
        skills.addAll(motivations);
        return skills;
    }
}
