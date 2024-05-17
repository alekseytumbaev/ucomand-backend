package com.example.ucomandbackend.profession;

import com.example.ucomandbackend.profession.api.ProfessionDto;
import com.example.ucomandbackend.util.Default200Controller;
import com.example.ucomandbackend.util.PageableMapper;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professions")
@RequiredArgsConstructor
public class ProfessionController implements Default200Controller {

    private final ProfessionService professionService;

    @PostMapping
    public ProfessionDto addProfession(@RequestBody @Validated ProfessionDto professionDto) {
        return professionService.addProfession(professionDto);
    }

    @DeleteMapping("/{professionId}")
    public void deleteProfessionById(@PathVariable Long professionId) {
        professionService.deleteProfessionById(professionId);
    }

    @GetMapping
    public List<ProfessionDto> getAllProfessions(@RequestParam(required = false, defaultValue = "0")
                                                 @Parameter(description = "min: 0")
                                                 @Validated @Min(0) Integer page,
                                                 @RequestParam(required = false, defaultValue = "10")
                                                 @Parameter(description = "min: 1")
                                                 @Validated @Min(1) Integer size) {
        return professionService.getAllProfessions(PageableMapper.toPageableDto(page, size));
    }

    @GetMapping("/{professionId}")
    public ProfessionDto getProfessionById(@PathVariable Long professionId) {
        return professionService.getProfessionById(professionId);
    }

    @PutMapping
    public ProfessionDto updateProfessionById(@RequestBody Long professionId, @RequestBody ProfessionDto professionDto) {
        return professionService.updateProfessionById(professionId, professionDto);
    }
}

