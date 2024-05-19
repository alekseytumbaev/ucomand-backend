package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.error_handling.NotFoundException;
import com.example.ucomandbackend.tags.dto.TagAvailabilityStatus;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.tags.dto.TagType;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepo;

    /**
     * @throws org.springframework.dao.DataIntegrityViolationException не уникальное название тега
     */
    @Transactional
    public TagDto addTag(TagDto tagDto) {
        tagDto.setId(null);
        var tag = tagRepo.save(TagMapper.toTag(tagDto));
        return TagMapper.toTagDto(tag);
    }

    public void deleteTagById(Long tagId) {
        tagRepo.deleteById(tagId);
    }

    /**
     * @throws org.springframework.dao.DataIntegrityViolationException не уникальное название тега
     */
    @Transactional
    public TagDto updateTagById(Long tagId, TagDto tagDto) {
        tagDto.setId(tagId);
        var tag = tagRepo.save(TagMapper.toTag(tagDto));
        return TagMapper.toTagDto(tag);
    }

    /**
     * @throws NotFoundException
     */
    @Transactional(readOnly = true)
    public TagDto getTagById(Long tagId) {
        var tag = tagRepo.getTagById(tagId).orElseThrow(() -> new NotFoundException("Тег не найден"));
        return TagMapper.toTagDto(tag);
    }

    @Transactional(readOnly = true)
    public List<TagDto> getAllTags(PageableDto pageableDto, List<TagType> types,
                                   List<TagAvailabilityStatus> avStatuses) {
        var tags = tagRepo.getAllByAvailabilityStatusInAndTypeIn(PageableMapper.toPageable(pageableDto), avStatuses, types);
        return tags.stream().map(TagMapper::toTagDto).toList();
    }

    @Transactional(readOnly = true)
    public List<Tag> getAllTagsByNames(List<String> tagNames) {
        return tagRepo.getAllByNameIn(tagNames);
    }

    @Transactional(readOnly = true)
    public Set<Tag> getTagsByResumeId(Long resumeId) {
        return tagRepo.getTagsByResumeId(resumeId);
    }

    @Transactional(readOnly = true)
    public Tag getTagByName(String name) {
        return tagRepo.findByName(name).orElseThrow(() -> new NotFoundException("Тег не найден"));
    }
}
