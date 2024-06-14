package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.ad.dto.VisibilityLevel;
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
        return (root, query, cb) -> userId == null ? cb.conjunction() : cb.equal(root.get(Ad.F.type), userId);
    }

    public Specification<Ad> gendersIn(Set<Gender> genders) {
        return (root, query, cb) -> CollectionUtils.isEmpty(genders)
                ? cb.conjunction()
                : cb.in(root.get(Ad.F.user).get(User.F.gender)).value(genders);
    }

    public Specification<Ad> ageGte(Integer age) {
        return (root, query, cb) -> age == null
                ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get(Ad.F.user).get(User.F.age), age);
    }

    public Specification<Ad> ageLte(Integer age) {
        return (root, query, cb) -> age == null
                ? cb.conjunction()
                : cb.lessThanOrEqualTo(root.get(Ad.F.user).get(User.F.age), age);
    }

    public Specification<Ad> cityOfResidenceIn(Set<String> cities) {
        return (root, query, cb) -> CollectionUtils.isEmpty(cities)
                ? cb.conjunction()
                : cb.in(root.get(Ad.F.user).get(User.F.cityOfResidence)).value(cities);
    }

    public Specification<Ad> tagsIn(Set<TagDto> tags) {
        return (root, query, cb) -> {
            if (CollectionUtils.isEmpty(tags)) {
                return cb.conjunction();
            }
            var predicates = new HashSet<Predicate>();
            for (TagDto tag : tags) {
                var tagAndCompetenceLevelPredicate = new ArrayList<Predicate>(2);
                tagAndCompetenceLevelPredicate.add(
                        cb.equal(root.get(Ad.F.tags).get(AdCompetenceLevelTag.F.tag).get(Tag.F.id), tag.getId()));
                if (tag.getCompetenceLevel() != null) {
                    var predicate = cb.greaterThanOrEqualTo(
                            root.get(Ad.F.tags).get(AdCompetenceLevelTag.F.competenceLevel), tag.getCompetenceLevel());
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
