package com.example.ucomandbackend.city;

import com.example.ucomandbackend.city.dto.CityDto;
import com.example.ucomandbackend.error_handling.common_exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<CityDto> getAllCities() {
        var cities = cityRepository.findAllWithRegionAndDistrict();
        return cities.stream().map(CityMapper::toDto).toList();
    }

    /**
     * @throws NotFoundException город не найден
     */
    public City getCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new NotFoundException("Город не найден"));
    }
}
