package com.example.ucomandbackend.ad.dto;

import com.example.ucomandbackend.ad.AdType;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.dto.UserDto;
import jakarta.validation.Valid;
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
    public ResumeDto(Long id,
                     @Valid UserDto user,
                     @Valid TagDto profession,
                     @Valid @NotNull Set<TagDto> skills,
                     @NotNull MotivationType motivation,
                     String freeLink,
                     String ownLink,
                     String contacts,
                     String details,
                     VisibilityLevel visibility,
                     OffsetDateTime creationDate) {
        super(id,
                user,
                profession,
                skills,
                motivation,
                freeLink,
                ownLink,
                contacts,
                details,
                visibility,
                creationDate);
    }

    @Override
    public AdType getAdType() {
        return AdType.RESUME;
    }
}
