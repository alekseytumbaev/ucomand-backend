package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.tags.dto.TagType;
import com.example.ucomandbackend.util.OnCreate;
import com.example.ucomandbackend.util.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    public TagDto addTag(@RequestBody @Validated({OnCreate.class}) TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    //TODO только админ
    @DeleteMapping("/{tagId}")
    public void deleteTagById(@PathVariable Long tagId) {
        tagService.deleteTagById(tagId);
    }

    @Operation(description = "Если в types пустой список, будут выбраны все теги")
    @GetMapping
    public Collection<TagDto> getAllTags(@RequestParam(required = false, defaultValue = "") List<TagType> types) {
        return tagService.getAllTags(types);
    }

    @GetMapping("/{tagId}")
    public TagDto getTagById(@PathVariable Long tagId) {
        return tagService.getTagById(tagId);
    }

    //TODO только админ
    @PutMapping("/{tagId}")
    public TagDto updateTagById(@PathVariable Long tagId, @RequestBody @Validated({OnUpdate.class}) TagDto tagDto) {
        return tagService.updateTagById(tagId, tagDto);
    }
}

