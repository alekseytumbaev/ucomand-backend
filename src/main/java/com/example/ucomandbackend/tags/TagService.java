package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.error_handling.common_exception.NotFoundException;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.tags.dto.TagType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepo;

    //TODO сравнивать имя игнор-кейсом
    /**
     * @throws org.springframework.dao.DataIntegrityViolationException не уникальное название тега
     */
    @Transactional
    public TagDto addTag(TagDto tagDto) {
        tagDto.setId(null);
        tagDto.setName(tagDto.getName().strip());
        var tag = tagRepo.save(TagMapper.toTag(tagDto));
        return TagMapper.toTagDto(tag);
    }

    public void deleteTagById(Long tagId) {
        tagRepo.deleteById(tagId);
    }

    //TODO сравнивать имя игнор-кейсом
    /**
     * @throws org.springframework.dao.DataIntegrityViolationException не уникальное название тега
     */
    @Transactional
    public TagDto updateTagById(Long tagId, TagDto tagDto) {
        tagDto.setId(tagId);
        tagDto.setName(tagDto.getName().strip());
        var tag = tagRepo.save(TagMapper.toTag(tagDto));
        return TagMapper.toTagDto(tag);
    }

    /**
     * @throws NotFoundException
     */
    @Transactional(readOnly = true)
    public TagDto getTagById(Long tagId) {
        var tag = tagRepo.findById(tagId).orElseThrow(() -> new NotFoundException("Тег не найден"));
        return TagMapper.toTagDto(tag);
    }

    /**
     * @param types типы тегов, если пустой - будут выбраны все
     */
    @Transactional(readOnly = true)
    public List<TagDto> getAllTags(List<TagType> types) {
        var tags = types.isEmpty() ? tagRepo.findAll() : tagRepo.findAllByTypeIn(types);
        return tags.stream().map(TagMapper::toTagDto).toList();
    }

    @Transactional(readOnly = true)
    public List<Tag> getAllTagsByIds(Set<Long> ids) {
        return tagRepo.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public List<Tag> getAllTagsByNames(List<String> tagNames) {
        return tagRepo.findAllByNameIn(tagNames);
    }
}
