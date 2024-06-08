package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.ad.dto.AdDto;
import com.example.ucomandbackend.ad.exception.AdDoesntBelongToUserException;
import com.example.ucomandbackend.error_handling.common_exception.CorruptedTokenException;
import com.example.ucomandbackend.error_handling.common_exception.NotFoundException;
import com.example.ucomandbackend.security.AuthUtils;
import com.example.ucomandbackend.tags.TagMapper;
import com.example.ucomandbackend.tags.TagService;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.UserService;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepo;
    private final AdCompetenceLevelTagRepository competenceLevelTagRepo;
    private final UserService userService;
    private final TagService tagService;


    /**
     * @throws NotFoundException       пользователь не найден
     * @throws CorruptedTokenException
     */
    @Transactional
    public AdDto addAdForCurrentUser(AdDto adDto) {
        adDto.setId(null);
        adDto.setCreationDate(OffsetDateTime.now());
        long userId = AuthUtils.extractUserIdFromJwt();
        return saveAdOfUser(adDto, userService.getUserById(userId));
    }

    /**
     * @throws NotFoundException             пользователь или объявление не найдено
     * @throws AdDoesntBelongToUserException объявление не принадлежит пользователю
     * @throws CorruptedTokenException
     */
    @Transactional
    public AdDto updateAdOfCurrentUser(Long adId, AdDto adDto) {
        var ad = adRepo.findByTypeAndId(adDto.getAdType(), adId)
                .orElseThrow(() -> new NotFoundException("Объявление не найдено"));
        if (ad.getUser().getId() != AuthUtils.extractUserIdFromJwt()) {
            throw new AdDoesntBelongToUserException("Объявление не принадлежит пользователю");
        }
        return updateAdById(adId, adDto);
    }

    /**
     * @throws NotFoundException объявление не найдено
     */
    @Transactional
    public AdDto updateAdById(Long adId, AdDto adDto) {
        var ad = adRepo.findByTypeAndId(adDto.getAdType(), adId)
                .orElseThrow(() -> new NotFoundException("Объявление не найдено"));
        adDto.setId(adId);
        adDto.setCreationDate(ad.getCreationDate());
        ad.getTags().clear();
        adRepo.save(ad);

        return saveAdOfUser(adDto, ad.getUser());
    }


    @Transactional
    public AdDto saveAdOfUser(AdDto adDto, User user) {
        var ad = adRepo.save(AdMapper.toAdd(adDto, user, new HashSet<>()));

        Map<Long, TagDto> idsToTagDtos = getTagsFromAdDto(adDto);
        var tags = tagService.getAllTagsByIds(idsToTagDtos.keySet());

        var competenceLevelTags = tags.stream()
                .map(tag -> TagMapper.toAdCompetenceLevelTag(
                        null, tag, ad, idsToTagDtos.get(tag.getId()).getCompetenceLevel()))
                .collect(Collectors.toSet());

        competenceLevelTagRepo.saveAll(competenceLevelTags);
        ad.addTags(competenceLevelTags);

        return AdMapper.toAdDto(adRepo.save(ad));
    }

    private Map<Long, TagDto> getTagsFromAdDto(AdDto adDto) {
        Map<Long, TagDto> idsToTags = adDto.getSkills().stream().collect(Collectors.toMap(TagDto::getId, it -> it));
        idsToTags.put(adDto.getProfession().getId(), adDto.getProfession());
        return idsToTags;
    }

    /**
     * @throws CorruptedTokenException
     */
    @Transactional(readOnly = true)
    public List<AdDto> getAllAdsOfCurrentUser(AdType adType) {
        long userId = AuthUtils.extractUserIdFromJwt();
        return getAllAdsByUserId(adType, userId);
    }

    @Transactional(readOnly = true)
    public List<AdDto> getAllAdsByUserId(AdType adType, Long userId) {
        var ads = adRepo.findAllByTypeAndUser_Id(adType, userId);
        return ads.stream().map(AdMapper::toAdDto).toList();
    }

    /**
     * @throws NotFoundException объявление не найдено
     */
    @Transactional(readOnly = true)
    public AdDto getAdById(AdType adType, Long adId) {
        var ad = adRepo.findByTypeAndId(adType, adId).orElseThrow(() -> new NotFoundException("Объявление не найдено"));
        return AdMapper.toAdDto(ad);
    }

    @Transactional(readOnly = true)
    public List<AdDto> getAllAds(PageableDto pageableDto, AdType adType) {
        Page<Ad> ads = adRepo.findAllByType(PageableMapper.toPageable(pageableDto), adType);
        return ads.stream().map(AdMapper::toAdDto).toList();
    }

    //TODO только свое или админ
    @Transactional
    public void deleteAdById(AdType adType, Long adId) {
        adRepo.deleteByTypeAndId(adType, adId);
    }
}
