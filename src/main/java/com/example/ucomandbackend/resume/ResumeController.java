package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.resume.dto.ResumeDto;
import com.example.ucomandbackend.util.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RequestMapping("/resumes")
@RestController
@Validated
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/forCurrentUser")
    @Operation(description = "userDto передавать не нужно, оно будет игнорироваться")
    public ResumeDto addResumeForCurrentUser(@RequestBody @Validated ResumeDto resumeDto) {
        return resumeService.addResumeForCurrentUser(resumeDto);
    }

    @PutMapping("/ofCurrentUser/{resumeId}")
    public ResumeDto updateResumeOfCurrentUser(@PathVariable Long resumeId,
                                               @RequestBody @Validated ResumeDto resumeDto) {
        return resumeService.updateResumeOfCurrentUser(resumeId, resumeDto);
    }

    @GetMapping("/ofCurrentUser")
    public Collection<ResumeDto> getAllResumesOfCurrentUser() {
        return resumeService.getAllResumesOfCurrentUser();
    }

    //TODO видимость?
    @GetMapping("/byUserId")
    public Collection<ResumeDto> getAllResumesByUserId(@RequestParam Long userId) {
        return resumeService.getAllResumesByUserId(userId);
    }

    @GetMapping("/{resumeId}")
    public ResumeDto getResumeById(@PathVariable Long resumeId) {
        return resumeService.getResumeById(resumeId);
    }

    @GetMapping
    @Operation(description = "Если в tagIds пустой список, будут выбраны все резюме")
    public Collection<ResumeDto> getAllResumes(@RequestParam(defaultValue = "0")
                                               @Validated @Min(0) Integer page,

                                               @RequestParam(defaultValue = "10")
                                               @Validated @Min(1) Integer size,

                                               @RequestParam(defaultValue = "") List<Long> tagIds) {
        return resumeService.getAllResumes(PageableMapper.toPageableDto(page, size), tagIds);
    }

    //TODO только админ
    @PutMapping("/{resumeId}")
    public Collection<ResumeDto> updateResumeById(@PathVariable Long resumeId,
                                                  @RequestBody @Validated ResumeDto resumeDto) {
        return null;
//        return resumeService.updateResumeById(resumeId, resumeDto);
    }

    //TODO только свое или админ
    @DeleteMapping("/{resumeId}")
    public void deleteResumeById(@PathVariable Long resumeId) {
        resumeService.deleteResumeById(resumeId);
    }
}
