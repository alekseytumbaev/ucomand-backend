package com.example.ucomandbackend.resume;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/resumes")
@RestController
@Validated
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class ResumeController {

    private final ResumeService resumeService;

    @Schema(description = "UserDto передавать не нужно, оно будет игнорироваться")
    @PostMapping
    public ResumeDto addResumeForCurrentUser(@RequestBody @Validated ResumeDto resumeDto) {
        return resumeService.addResumeForCurrentUser(resumeDto);
    }

    @GetMapping("/resumesOfCurrentUser")
    public Collection<ResumeDto> getResumesOfCurrentUser() {
        return resumeService.getResumesOfCurrentUser();
    }

    @GetMapping
    public Collection<ResumeDto> getResumesByUserId(@RequestParam Long userId) {
        return resumeService.getResumesByUserId(userId);
    }
}
