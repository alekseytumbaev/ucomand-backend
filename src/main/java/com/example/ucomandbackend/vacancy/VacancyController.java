package com.example.ucomandbackend.vacancy;

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

@RequestMapping("/vacancies")
@RestController
@Validated
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class VacancyController {

    private final VacancyService vacancyService;

    @Operation(description = "projectId будет игнорироваться")
    @PostMapping("/forCurrentUser")
    public VacancyDto addVacancyForCurrentUser(@RequestBody @Validated VacancyDto vacancyDto) {
        return vacancyService.addVacancyForCurrentUser(vacancyDto);
    }

    @GetMapping
    @Operation(description = "Если в tagIds пустой список, будут выбраны все резюме")
    public Collection<VacancyDto> getAllVacanciesByTagIds(
            @RequestParam(defaultValue = "0")
            @Validated @Min(0) Integer page,

            @RequestParam(defaultValue = "10")
            @Validated @Min(1) Integer size,

            @RequestParam(defaultValue = "") List<Long> tagIds) {
        return vacancyService.getAllVacanciesByTagIds(PageableMapper.toPageableDto(page, size), tagIds);
    }

    @GetMapping("/ofCurrentUser")
    public Collection<VacancyDto> getAllVacanciesOfCurrentUser() {
        return vacancyService.getAllVacanciesOfCurrentUser();
    }

    @GetMapping("/{vacancyId}")
    public VacancyDto getVacancyById(@PathVariable Long vacancyId) {
        return vacancyService.getVacancyById(vacancyId);
    }

    @DeleteMapping("/{vacancyId}")
    public void deleteVacancyById(@PathVariable Long vacancyId) {
        vacancyService.deleteVacancyById(vacancyId);
    }
}
