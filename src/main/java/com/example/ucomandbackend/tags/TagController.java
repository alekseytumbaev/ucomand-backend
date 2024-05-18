package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.tags.dto.TagAvailabilityStatus;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.tags.dto.TagType;
import com.example.ucomandbackend.util.PageableMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/tags")
@Validated
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class TagController {

    private final TagService tagService;

    @PostMapping
    public TagDto addTag(@RequestBody @Validated TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{tagId}")
    public void deleteTagById(@PathVariable Long tagId) {
        tagService.deleteTagById(tagId);
    }

    @GetMapping
    public Collection<TagDto> getAllTags(@RequestParam(required = false, defaultValue = "0")
                                         @Parameter(description = "min: 0")
                                         @Validated @Min(0) Integer page,

                                         @RequestParam(required = false, defaultValue = "10")
                                         @Parameter(description = "min: 1")
                                         @Validated @Min(1) Integer size,

                                         @RequestParam(required = false, defaultValue = "PROFESSION,SKILL,MISC")
                                         List<TagType> types,

                                         @RequestParam(required = false, defaultValue = "AVAILABLE,VERIFICATION,UNAVAILABLE")
                                         List<TagAvailabilityStatus> avStatuses
    ) {
        return tagService.getAllTags(PageableMapper.toPageableDto(page, size), types, avStatuses);
    }

    @GetMapping("/{tagId}")
    public TagDto getTagById(@PathVariable Long tagId) {
        return tagService.getTagById(tagId);
    }

    @PutMapping("/{tagId}")
    public TagDto updateTagById(@PathVariable Long tagId, @RequestBody @Validated TagDto tagDto) {
        return tagService.updateTagById(tagId, tagDto);
    }
}

