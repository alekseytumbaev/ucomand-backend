package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.error_handling.common_exception.CorruptedTokenException;
import com.example.ucomandbackend.error_handling.common_exception.NotFoundException;
import com.example.ucomandbackend.resume.dto.ResumeDto;
import com.example.ucomandbackend.tags.Tag;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepo;
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
        var user = userService.getUserById(userId);
        Set<Tag> tags = new HashSet<>(
                tagService.getAllTagsByNames(resumeDto.getTags().stream().map(TagDto::getName).toList()));

        return null;// TODO
//        var resume = resumeRepo.save(ResumeMapper.toResume(resumeDto, user, tags));
//        return ResumeMapper.toResumeDto(resume);
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
        return null; //TODO
//        return resumes.stream().map(ResumeMapper::toResumeDto).toList();
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
    public List<ResumeDto> getAllResumesByTagIds(PageableDto pageableDto, List<Long> tagIds) {
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
