package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.ad.dto.AdFilterDto;
import com.example.ucomandbackend.tags.dto.TagDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@UtilityClass
public class AdSpecs {

    public Specification<Ad> where(@Nullable AdFilterDto adFilterDto) {
        return (root, query, cb) -> {
            if (adFilterDto == null) {
                return cb.conjunction();
            }
            var predicates = new HashSet<Predicate>();

            //adType
            predicates.add(cb.equal(root.get("type"), adFilterDto.getAdType()));

            //userId
            if (adFilterDto.getUserId() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), adFilterDto.getUserId()));
            }

            //genders
            if (!isEmpty(adFilterDto.getGenders())) {
                predicates.add(cb.in(root.get("user").get("gender")).value(adFilterDto.getGenders()));
            }

            //age
            if (adFilterDto.getAgeFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("user").get("age"), adFilterDto.getAgeFrom()));
            }
            if (adFilterDto.getAgeTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("user").get("age"), adFilterDto.getAgeTo()));
            }

            //cities
            if (!isEmpty(adFilterDto.getCities())) {
                predicates.add(cb.in(root.get("user").get("cityOfResidence")).value(adFilterDto.getCities()));
            }

            //tags
            if (!isEmpty(adFilterDto.getTags())) {
                predicates.add(tagsIn(root, cb, adFilterDto.getTags()));
            }

            //visibilities
            if (!isEmpty(adFilterDto.getVisibilities())) {
                predicates.add(cb.in(root.get("visibility")).value(adFilterDto.getVisibilities()));
            }

            //creationDate
            if (adFilterDto.getCreationDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), adFilterDto.getCreationDateFrom()));
            }
            if (adFilterDto.getCreationDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), adFilterDto.getCreationDateTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Predicate tagsIn(Root<Ad> root, CriteriaBuilder cb, Set<TagDto> tags) {
        var predicates = new HashSet<Predicate>();
        for (TagDto tag : tags) {
            var tagAndCompetenceLevelPredicate = new ArrayList<Predicate>(2);
            tagAndCompetenceLevelPredicate.add(cb.equal(root.get("tags").get("tag").get("id"), tag.getId()));
            if (tag.getCompetenceLevel() != null) {
                tagAndCompetenceLevelPredicate.add(
                        cb.greaterThanOrEqualTo(root.get("tags").get("competenceLevel"), tag.getCompetenceLevel()));
            }
            predicates.add(cb.and(tagAndCompetenceLevelPredicate.toArray(new Predicate[0])));
        }
        return cb.or(predicates.toArray(new Predicate[0]));
    }
}
