package com.example.ucomandbackend.ad.dto;

import com.example.ucomandbackend.ad.AdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VacancyFilterDto extends AdFilterDto {

    @Override
    public AdType getAdType() {
        return AdType.VACANCY;
    }
}
