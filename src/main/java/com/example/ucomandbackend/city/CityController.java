package com.example.ucomandbackend.city;

import com.example.ucomandbackend.city.dto.CityDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class CityController {

    private final CityService cityService;

    @GetMapping
    @SecurityRequirements
    public Collection<CityDto> getAllCities() {
        return cityService.getAllCities();
    }
}
