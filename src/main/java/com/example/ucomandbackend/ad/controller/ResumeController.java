package com.example.ucomandbackend.ad.controller;

import com.example.ucomandbackend.ad.AdMapper;
import com.example.ucomandbackend.ad.AdService;
import com.example.ucomandbackend.ad.AdSorter;
import com.example.ucomandbackend.ad.dto.ResumeDto;
import com.example.ucomandbackend.ad.dto.ResumeFilterDto;
import com.example.ucomandbackend.util.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.example.ucomandbackend.ad.AdType.RESUME;

@RequestMapping("/resumes")
@RestController
@Validated
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class ResumeController {

    private final AdService adService;

    @PostMapping("/forCurrentUser")
    @Operation(description = "userDto передавать не нужно, оно будет игнорироваться")
    public ResumeDto addResumeForCurrentUser(@RequestBody @Validated ResumeDto resumeDto) {
        return AdMapper.toResumeDto(adService.addAdForCurrentUser(resumeDto));
    }

    @PutMapping("/ofCurrentUser/{resumeId}")
    public ResumeDto updateResumeOfCurrentUser(@PathVariable Long resumeId,
                                               @RequestBody @Validated ResumeDto resumeDto) {
        return AdMapper.toResumeDto(adService.updateAdOfCurrentUser(resumeId, resumeDto));
    }

    @GetMapping("/ofCurrentUser")
    public Collection<ResumeDto> getAllResumesOfCurrentUser() {
        return (Collection) adService.getAllAdsOfCurrentUser(RESUME);
    }

    @GetMapping("/{resumeId}")
    @SecurityRequirements
    public ResumeDto getResumeById(@PathVariable Long resumeId) {
        return AdMapper.toResumeDto(adService.getAdById(RESUME, resumeId));
    }

    @PostMapping("/getAll")
    @SecurityRequirements
    public Collection<ResumeDto> getAllResumes(@RequestParam(defaultValue = "0")
                                               @Validated @Min(0) Integer page,

                                               @RequestParam(defaultValue = "10")
                                               @Validated @Min(1) Integer size,

                                               @Parameter(description = "Строки вида path.to.field_desc или " +
                                                       "path.to.field, по умолчанию asc. " +
                                                       "Сортировать можно по: " + AdSorter.AVAILABLE_SORT_DESCRIPTION)
                                               @RequestParam(defaultValue = "")
                                               @Validated Collection<String> sorts,

                                               @RequestBody(required = false)
                                               @Validated ResumeFilterDto filterDto) {
        return (Collection) adService.getAllAds(PageableMapper.toPageableDto(page, size, sorts), filterDto);
    }

    //TODO только админ
    @PutMapping("/{resumeId}")
    public ResumeDto updateResumeById(@PathVariable Long resumeId,
                                      @RequestBody @Validated ResumeDto resumeDto) {
        return AdMapper.toResumeDto(adService.updateAdById(resumeId, resumeDto));
    }

    //TODO только свое или админ
    @DeleteMapping("/{resumeId}")
    public void deleteResumeById(@PathVariable Long resumeId) {
        adService.deleteAdById(RESUME, resumeId);
    }
}
