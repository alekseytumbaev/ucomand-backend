package com.example.ucomandbackend.ad.dto;

import com.example.ucomandbackend.ad.Ad;
import com.example.ucomandbackend.ad.AdType;
import com.example.ucomandbackend.tag.dto.TagDto;
import com.example.ucomandbackend.user.dto.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.Set;

import static com.example.ucomandbackend.ad.AdSpecs.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdFilterDto {
    private Long userId;
    private Set<Gender> genders;
    private Integer ageGte;
    private Integer ageLte;
    private Set<String> citiesOfResidence;
    private Set<TagDto> tags;
    private Set<VisibilityLevel> visibilities;
    private OffsetDateTime creationDateGte;
    private OffsetDateTime creationDateLte;

    @JsonIgnore
    public AdType getAdType() {
        throw new NotImplementedException("Тип объявления не задан");
    }

    public Specification<Ad> toSpecification() {
        return Specification.where(typeEqual(getAdType()))
                .and(userIdEqual(userId))
                .and(gendersIn(genders))
                .and(ageGte(ageGte))
                .and(ageLte(ageLte))
                .and(cityOfResidenceIn(citiesOfResidence))
                .and(tagsIn(tags))
                .and(visibilityIn(visibilities))
                .and(creationDateGte(creationDateGte))
                .and(creationDateLte(creationDateLte));
    }
}
