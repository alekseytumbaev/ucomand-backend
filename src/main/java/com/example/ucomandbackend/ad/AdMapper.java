package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.ad.dto.AdDto;
import com.example.ucomandbackend.ad.dto.ResumeDto;
import com.example.ucomandbackend.ad.dto.VacancyDto;
import com.example.ucomandbackend.tags.TagMapper;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserMapper;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class AdMapper {

    public ResumeDto toResumeDto(AdDto adDto) {
        return new ResumeDto(
                adDto.getId(),
                adDto.getUser(),
                adDto.getProfession(),
                adDto.getSkills(),
                adDto.getMotivation(),
                adDto.getFreeLink(),
                adDto.getOwnLink(),
                adDto.getContacts(),
                adDto.getDetails(),
                adDto.getVisibility(),
                adDto.getCreationDate()
        );
    }

    public VacancyDto toVacancyDto(AdDto adDto) {
        return new VacancyDto(
                adDto.getId(),
                adDto.getUser(),
                adDto.getProfession(),
                adDto.getSkills(),
                adDto.getMotivation(),
                adDto.getFreeLink(),
                adDto.getOwnLink(),
                adDto.getContacts(),
                adDto.getDetails(),
                adDto.getVisibility(),
                adDto.getCreationDate()
        );
    }

    public AdDto toAdDto(Ad ad) {
        var profession = ad.getProfession();
        return new AdDto(
                ad.getId(),
                UserMapper.toUserDtoWithoutTelegram(ad.getUser()),
                profession == null ? null : TagMapper.toTagDto(profession.getTag(), profession.getCompetenceLevel()),
                ad.getSkills().stream()
                        .map(it -> TagMapper.toTagDto(it.getTag(), it.getCompetenceLevel()))
                        .collect(Collectors.toSet()),
                ad.getMotivation(),
                ad.getFreeLink(),
                ad.getOwnLink(),
                ad.getContacts(),
                ad.getDetails(),
                ad.getVisibility(),
                ad.getCreationDate()
        );
    }

    public Ad toAdd(AdDto adDto, User user, Set<AdCompetenceLevelTag> competenceLevelTags) {
        return switch (adDto.getAdType()) {
            case RESUME -> toAdd(adDto, AdType.RESUME, user, competenceLevelTags);
            case VACANCY -> toAdd(adDto, AdType.VACANCY, user, competenceLevelTags);
        };
    }

    public Ad toAdd(AdDto adDto, AdType adType, User user, Set<AdCompetenceLevelTag> competenceLevelTags) {
        return new Ad(
                adDto.getId(),
                adType,
                user,
                competenceLevelTags,
                adDto.getMotivation(),
                adDto.getFreeLink(),
                adDto.getOwnLink(),
                adDto.getContacts(),
                adDto.getDetails(),
                adDto.getVisibility(),
                adDto.getCreationDate()
        );
    }
}
