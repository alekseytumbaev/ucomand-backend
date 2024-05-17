package com.example.ucomandbackend.tags;

import io.swagger.v3.oas.annotations.Parameter;
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
public class TagController {

    @PostMapping
    public TagDto addTag(@RequestBody @Validated TagDto tagDto) {
        return null;
    }

    @DeleteMapping("/{tagId}")
    public void deleteTagById(@PathVariable Long tagId) {
        return;
    }

    @GetMapping
    public Collection<TagDto> getAllTags(@RequestParam(required = false, defaultValue = "0")
                                         @Parameter(description = "min: 0")
                                         @Validated @Min(0) Integer page,

                                         @RequestParam(required = false, defaultValue = "10")
                                         @Parameter(description = "min: 1")
                                         @Validated @Min(1) Integer size,

                                         @RequestParam(required = false, defaultValue = "PROFESSION,LANGUAGE,FRAMEWORK,MISC")
                                         List<TagType> types,

                                         @RequestParam(required = false, defaultValue = "AVAILABLE,VERIFICATION,UNAVAILABLE")
                                         List<TagAvailabilityStatus> avStatuses
    ) {
        return null;
    }

    @GetMapping("/{tagId}")
    public TagDto getTagById(@PathVariable Long tagId) {
        return null;
    }

    @PutMapping("/{tagId}")
    public TagDto updateTagById(@PathVariable Long tagId, @RequestBody @Validated TagDto tagDto) {
        return null;
    }
}

