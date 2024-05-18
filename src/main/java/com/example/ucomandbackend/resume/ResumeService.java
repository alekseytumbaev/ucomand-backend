package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.tags.TagService;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.UserService;
import com.example.ucomandbackend.util.AuthUtils;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @throws com.example.ucomandbackend.error_handling.NotFoundException       пользователь не найден
     * @throws com.example.ucomandbackend.error_handling.CorruptedTokenException
     */
    @Transactional
    public ResumeDto addResumeForCurrentUser(ResumeDto resumeDto) {
        resumeDto.setId(null);
        long userId = AuthUtils.extractUserIdFromJwt();
        var user = userService.getUserById(userId);
        Set<Tag> tags = new HashSet<>(
                tagService.getAllTagsByNames(resumeDto.getTags().stream().map(TagDto::getName).toList()));

        var resume = resumeRepo.save(ResumeMapper.toResume(resumeDto, user, tags));
        return ResumeMapper.toResumeDto(resume);
    }

    /**
     * @throws com.example.ucomandbackend.error_handling.CorruptedTokenException
     */
    @Transactional(readOnly = true)
    public Collection<ResumeDto> getResumesOfCurrentUser() {
        long userId = AuthUtils.extractUserIdFromJwt();
        return getResumesByUserId(userId);
    }

    public List<ResumeDto> getResumesByUserId(Long userId) {
        var resumes = resumeRepo.findAllByUser_Id(userId);
        return resumes.stream().map(ResumeMapper::toResumeDto).toList();
    }
}
