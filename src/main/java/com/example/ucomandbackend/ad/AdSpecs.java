package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.ad.dto.VisibilityLevel;
import com.example.ucomandbackend.city.City;
import com.example.ucomandbackend.city.District;
import com.example.ucomandbackend.city.Region;
import com.example.ucomandbackend.tag.Tag;
import com.example.ucomandbackend.tag.dto.TagDto;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.dto.Gender;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class AdSpecs {

    public Specification<Ad> typeEqual(AdType adType) {
        return (root, query, cb) -> adType == null ? cb.disjunction() : cb.equal(root.get(Ad.F.type), adType);
    }

    public Specification<Ad> userIdEqual(Long userId) {
        return (root, query, cb) -> userId == null
                ? cb.conjunction()
                : cb.equal(root.join(Ad.F.user).get(User.F.id), userId);
    }

    public Specification<Ad> gendersIn(Set<Gender> genders) {
        return (root, query, cb) -> CollectionUtils.isEmpty(genders)
                ? cb.conjunction()
                : cb.in(root.join(Ad.F.user).get(User.F.gender)).value(genders);
    }

    public Specification<Ad> ageGte(Integer age) {
        return (root, query, cb) -> age == null
                ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.join(Ad.F.user).get(User.F.age), age);
    }

    public Specification<Ad> ageLte(Integer age) {
        return (root, query, cb) -> age == null
                ? cb.conjunction()
                : cb.lessThanOrEqualTo(root.join(Ad.F.user).get(User.F.age), age);
    }

    public Specification<Ad> cityOfResidenceIdIn(Set<Long> cityIds) {
        return (root, query, cb) -> CollectionUtils.isEmpty(cityIds)
                ? cb.conjunction()
                : cb.in(root.join(Ad.F.user).join(User.F.cityOfResidence).get(City.F.id)).value(cityIds);
    }

    public Specification<Ad> regionIdIn(Set<Long> regionIds) {
        return (root, query, cb) -> CollectionUtils.isEmpty(regionIds)
                ? cb.conjunction()
                : cb.in(root.join(Ad.F.user)
                        .join(User.F.cityOfResidence)
                        .join(City.F.region)
                        .get(Region.F.id))
                .value(regionIds);
    }

    public Specification<Ad> districtIdIn(Set<Long> cityIds) {
        return (root, query, cb) -> CollectionUtils.isEmpty(cityIds)
                ? cb.conjunction()
                : cb.in(root.join(Ad.F.user)
                        .join(User.F.cityOfResidence)
                        .join(City.F.region)
                        .join(Region.F.district)
                        .get(District.F.id))
                .value(cityIds);
    }

    public Specification<Ad> tagsIn(Set<TagDto> tagDtos) {
        return (root, query, cb) -> {
            if (CollectionUtils.isEmpty(tagDtos)) {
                return cb.conjunction();
            }

            var predicates = new HashSet<Predicate>();
            for (TagDto tag : tagDtos) {
                var tagAndCompetenceLevelPredicate = new ArrayList<Predicate>(2);
                tagAndCompetenceLevelPredicate.add(
                        cb.equal(root.join(Ad.F.tags).join(AdCompetenceLevelTag.F.tag).get(Tag.F.id), tag.getId()));
                if (tag.getCompetenceLevel() != null) {
                    var predicate = cb.greaterThanOrEqualTo(
                            root.join(Ad.F.tags).get(AdCompetenceLevelTag.F.competenceLevel), tag.getCompetenceLevel());
                    tagAndCompetenceLevelPredicate.add(predicate);
                }
                predicates.add(cb.and(tagAndCompetenceLevelPredicate.toArray(new Predicate[0])));
            }
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Ad> visibilityIn(Set<VisibilityLevel> visibilities) {
        return (root, query, cb) -> CollectionUtils.isEmpty(visibilities)
                ? cb.conjunction()
                : cb.in(root.get(Ad.F.visibility)).value(visibilities);
    }

    public Specification<Ad> creationDateGte(OffsetDateTime creationDate) {
        return (root, query, cb) -> creationDate == null
                ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get(Ad.F.creationDate), creationDate);
    }

    public Specification<Ad> creationDateLte(OffsetDateTime creationDate) {
        return (root, query, cb) -> creationDate == null
                ? cb.conjunction()
                : cb.lessThanOrEqualTo(root.get(Ad.F.creationDate), creationDate);
    }
}
