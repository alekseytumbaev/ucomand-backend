package com.example.ucomandbackend.profession;

import com.example.ucomandbackend.profession.api.ProfessionDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfessionMapper {
    public ProfessionDto toProfessionDto(Profession profession) {
        return new ProfessionDto(profession.getId(), profession.getName());
    }

    public Profession toProfession(ProfessionDto professionDto) {
        return new Profession(professionDto.getId(), professionDto.getName());
    }
}
