package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.ad.dto.VisibilityLevel;
import com.example.ucomandbackend.tag.dto.TagType;
import com.example.ucomandbackend.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "ads")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(innerTypeName = "F")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private AdType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "ad_id")
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private Set<AdCompetenceLevelTag> tags = new HashSet<>();

    private String freeLink;

    private String ownLink;

    private String contacts;

    private String details;

    @Enumerated(EnumType.STRING)
    private VisibilityLevel visibility;

    private OffsetDateTime creationDate;


    public boolean addTag(AdCompetenceLevelTag tag) {
        boolean isModified = tags.add(tag);
        tag.setAd(this);
        return isModified;
    }

    public boolean addTags(Set<AdCompetenceLevelTag> tags) {
        boolean isModified = this.tags.addAll(tags);
        for (AdCompetenceLevelTag tag : tags) {
            tag.setAd(this);
        }
        return isModified;
    }

    @Nullable
    public AdCompetenceLevelTag getProfession() {
        return tags.stream()
                .filter(tag -> tag.getTag().getType() == TagType.PROFESSION)
                .findFirst()
                .orElse(null);
    }

    public Set<AdCompetenceLevelTag> getSkills() {
        return tags.stream()
                .filter(tag -> tag.getTag().getType() == TagType.SKILL)
                .collect(Collectors.toSet());
    }

    public Set<AdCompetenceLevelTag> getMotivations() {
        return tags.stream()
                .filter(tag -> tag.getTag().getType() == TagType.MOTIVATION)
                .collect(Collectors.toSet());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Ad ad = (Ad) o;
        return getId() != null && Objects.equals(getId(), ad.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
