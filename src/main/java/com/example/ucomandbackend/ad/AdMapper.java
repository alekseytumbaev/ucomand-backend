package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.ad.dto.AdDto;
import com.example.ucomandbackend.ad.dto.ResumeDto;
import com.example.ucomandbackend.ad.dto.VacancyDto;
import com.example.ucomandbackend.tag.TagMapper;
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
                adDto.getTitle(),
                adDto.getUser(),
                adDto.getProfession(),
                adDto.getSkills(),
                adDto.getMotivations(),
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
                adDto.getTitle(),
                adDto.getUser(),
                adDto.getProfession(),
                adDto.getSkills(),
                adDto.getMotivations(),
                adDto.getFreeLink(),
                adDto.getOwnLink(),
                adDto.getContacts(),
                adDto.getDetails(),
                adDto.getVisibility(),
                adDto.getCreationDate()
        );
    }

    public AdDto toAdDto(Ad ad) {
        return new AdDto(
                ad.getId(),
                ad.getTitle(),
                UserMapper.toUserDtoWithoutTelegram(ad.getUser()),
                ad.getProfession() == null ? null : TagMapper.toTagDto(ad.getProfession()),
                ad.getSkills().stream().map(TagMapper::toTagDto).collect(Collectors.toSet()),
                ad.getMotivations().stream().map(TagMapper::toTagDto).collect(Collectors.toSet()),
                ad.getFreeLink(),
                ad.getOwnLink(),
                ad.getContacts(),
                ad.getDetails(),
                ad.getVisibility(),
                ad.getCreationDate()
        );
    }

    public Ad toAdd(AdDto adDto, AdType adType, User user, Set<AdCompetenceLevelTag> competenceLevelTags) {
        return new Ad(
                adDto.getId(),
                adDto.getTitle(),
                adType,
                user,
                competenceLevelTags,
                adDto.getFreeLink(),
                adDto.getOwnLink(),
                adDto.getContacts(),
                adDto.getDetails(),
                adDto.getVisibility(),
                adDto.getCreationDate()
        );
    }

    public Ad toAdd(AdDto adDto, User user, Set<AdCompetenceLevelTag> competenceLevelTags) {
        return switch (adDto.getAdType()) {
            case RESUME -> toAdd(adDto, AdType.RESUME, user, competenceLevelTags);
            case VACANCY -> toAdd(adDto, AdType.VACANCY, user, competenceLevelTags);
        };
    }
}
