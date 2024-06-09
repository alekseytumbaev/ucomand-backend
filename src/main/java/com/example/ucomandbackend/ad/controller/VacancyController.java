package com.example.ucomandbackend.ad.controller;

import com.example.ucomandbackend.ad.AdMapper;
import com.example.ucomandbackend.ad.AdService;
import com.example.ucomandbackend.ad.dto.VacancyDto;
import com.example.ucomandbackend.ad.dto.VacancyFilterDto;
import com.example.ucomandbackend.util.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.example.ucomandbackend.ad.AdType.VACANCY;

@RequestMapping("/vacancies")
@RestController
@Validated
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class VacancyController {

    private final AdService adService;

    @PostMapping("/forCurrentUser")
    @Operation(description = "userDto передавать не нужно, оно будет игнорироваться")
    public VacancyDto addVacancyForCurrentUser(@RequestBody @Validated VacancyDto vacancyDto) {
        return AdMapper.toVacancyDto((adService.addAdForCurrentUser(vacancyDto)));
    }

    @PutMapping("/ofCurrentUser/{vacancyId}")
    public VacancyDto updateVacancyOfCurrentUser(@PathVariable Long vacancyId,
                                                 @RequestBody @Validated VacancyDto vacancyDto) {
        return AdMapper.toVacancyDto(adService.updateAdOfCurrentUser(vacancyId, vacancyDto));
    }

    @GetMapping("/ofCurrentUser")
    public Collection<VacancyDto> getAllVacanciesOfCurrentUser() {
        return (Collection) adService.getAllAdsOfCurrentUser(VACANCY);
    }

    @GetMapping("/{vacancyId}")
    @SecurityRequirements
    public VacancyDto getVacancyById(@PathVariable Long vacancyId) {
        return AdMapper.toVacancyDto(adService.getAdById(VACANCY, vacancyId));
    }

    @PostMapping("/getAll")
    @SecurityRequirements
    public Collection<VacancyDto> getAllVacancies(@RequestParam(defaultValue = "0")
                                                  @Validated @Min(0) Integer page,

                                                  @RequestParam(defaultValue = "10")
                                                  @Validated @Min(1) Integer size,

                                                  @RequestBody(required = false)
                                                  @Validated VacancyFilterDto filterDto) {
        return (Collection) adService.getAllAds(PageableMapper.toPageableDto(page, size), filterDto);
    }

    //TODO только админ
    @PutMapping("/{vacancyId}")
    public VacancyDto updateVacancyById(@PathVariable Long vacancyId,
                                        @RequestBody @Validated VacancyDto vacancyDto) {
        return AdMapper.toVacancyDto(adService.updateAdById(vacancyId, vacancyDto));
    }

    //TODO только свое или админ
    @DeleteMapping("/{vacancyId}")
    public void deleteVacancyById(@PathVariable Long vacancyId) {
        adService.deleteAdById(VACANCY, vacancyId);
    }
}
