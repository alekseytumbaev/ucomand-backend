package com.example.ucomandbackend.ad.dto;

import com.example.ucomandbackend.ad.AdType;
import com.example.ucomandbackend.tag.dto.TagDto;
import com.example.ucomandbackend.user.dto.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResumeDto extends AdDto {

    public ResumeDto(Long id, @NotBlank String title, @Valid UserDto user, @Valid @NotNull TagDto profession, @Valid @NotNull Set<TagDto> skills, @Valid @NotNull Set<TagDto> motivations, String freeLink, String ownLink, String contacts, String details, VisibilityLevel visibility, OffsetDateTime creationDate) {
        super(id, title, user, profession, skills, motivations, freeLink, ownLink, contacts, details, visibility, creationDate);
    }

    @Override
    public AdType getAdType() {
        return AdType.RESUME;
    }
}
