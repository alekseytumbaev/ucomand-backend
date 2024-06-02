package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.error_handling.common_exception.CorruptedTokenException;
import com.example.ucomandbackend.error_handling.common_exception.NotFoundException;
import com.example.ucomandbackend.resume.dto.ResumeDto;
import com.example.ucomandbackend.tags.TagMapper;
import com.example.ucomandbackend.tags.TagService;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.UserService;
import com.example.ucomandbackend.util.AuthUtils;
import com.example.ucomandbackend.util.CollectionUtils;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepo;
    private final ResumeCompetenceLevelTagRepository competenceLevelTagRepo;
    private final UserService userService;
    private final TagService tagService;


    /**
     * @throws NotFoundException       пользователь не найден
     * @throws CorruptedTokenException
     */
    @Transactional
    public ResumeDto addResumeForCurrentUser(ResumeDto resumeDto) {
        resumeDto.setId(null);
        resumeDto.setCreationDate(OffsetDateTime.now());

        long userId = AuthUtils.extractUserIdFromJwt();
        return saveResumeOfUser(userId, resumeDto);
    }

    /**
     * @throws NotFoundException       пользователь или резюме не найдено
     * @throws CorruptedTokenException
     */
    @Transactional
    public ResumeDto updateResumeOfCurrentUser(Long resumeId, ResumeDto resumeDto) {
        if (!resumeRepo.existsById(resumeId)) {
            throw new NotFoundException("Резюме не найдено");
        }
        competenceLevelTagRepo.deleteByResume_Id(resumeId);
        resumeDto.setId(resumeId);
        resumeDto.setCreationDate(resumeRepo.findCreationDateById(resumeId));

        long userId = AuthUtils.extractUserIdFromJwt();
        return saveResumeOfUser(userId, resumeDto);
    }

    /**
     * @throws NotFoundException пользователь не найден
     */
    @Transactional
    public ResumeDto saveResumeOfUser(Long userId, ResumeDto resumeDto) {
        var user = userService.getUserById(userId);

        var resume = resumeRepo.save(ResumeMapper.toResume(resumeDto, user, new HashSet<>()));

        Map<Long, TagDto> idsToTagDtos = getTagsFromResumeDto(resumeDto);
        var tags = tagService.getAllTagsByIds(idsToTagDtos.keySet());

        var competenceLevelTags = tags.stream()
                .map(tag -> TagMapper.toResumeCompetenceLevelTag(
                        null, tag, resume, idsToTagDtos.get(tag.getId()).getCompetenceLevel()))
                .collect(Collectors.toSet());

        competenceLevelTagRepo.saveAll(competenceLevelTags);
        resume.setTags(competenceLevelTags);

        return ResumeMapper.toResumeDto(resumeRepo.save(resume));
    }

    private Map<Long, TagDto> getTagsFromResumeDto(ResumeDto resumeDto) {
        Map<Long, TagDto> idsToTags = resumeDto.getSkills().stream().collect(Collectors.toMap(TagDto::getId, it -> it));
        idsToTags.put(resumeDto.getProfession().getId(), resumeDto.getProfession());
        return idsToTags;
    }

    /**
     * @throws CorruptedTokenException
     */
    @Transactional(readOnly = true)
    public Collection<ResumeDto> getAllResumesOfCurrentUser() {
        long userId = AuthUtils.extractUserIdFromJwt();
        return getAllResumesByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<ResumeDto> getAllResumesByUserId(Long userId) {
        var resumes = resumeRepo.findAllByUser_Id(userId);
        return resumes.stream().map(ResumeMapper::toResumeDto).toList();
    }

    /**
     * @throws NotFoundException резюме не найдено
     */
    @Transactional(readOnly = true)
    public ResumeDto getResumeById(Long resumeId) {
        var resume = resumeRepo.findById(resumeId).orElseThrow(() -> new NotFoundException("Резюме не найдено"));
        return null; //TODO
//        return ResumeMapper.toResumeDto(resume);
    }

    /**
     * Если tagIds пустой, возвращаются все резюме
     */
    @Transactional(readOnly = true)
    public List<ResumeDto> getAllResumes(PageableDto pageableDto, List<Long> tagIds) {
        Page<Resume> resumes;
        if (CollectionUtils.notEmpty(tagIds)) {
            resumes = resumeRepo.findAllByTagIdsIn(PageableMapper.toPageable(pageableDto), tagIds);
        } else {
            resumes = resumeRepo.findAll(PageableMapper.toPageable(pageableDto));
        }
        return null; //TODO
//        return resumes.stream().map(ResumeMapper::toResumeDto).toList();
    }

    @Transactional
    public void deleteResumeById(Long resumeId) {
        resumeRepo.deleteById(resumeId);
    }
}
