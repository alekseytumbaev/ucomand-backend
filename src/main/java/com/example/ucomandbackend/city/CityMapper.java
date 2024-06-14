package com.example.ucomandbackend.city;

import com.example.ucomandbackend.city.dto.CityDto;
import com.example.ucomandbackend.city.dto.DistrictDto;
import com.example.ucomandbackend.city.dto.RegionDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CityMapper {

    public CityDto toDto(City city) {
        var region = city.getRegion();
        var districtDto = new DistrictDto(region.getDistrict().getId(), region.getDistrict().getName());
        var regionDto = new RegionDto(region.getId(), region.getName(), districtDto);
        return new CityDto(
                city.getId(),
                city.getName(),
                regionDto
        );
    }
}
