package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.ad.dto.MotivationType;
import com.example.ucomandbackend.ad.dto.VisibilityLevel;
import com.example.ucomandbackend.tags.dto.TagType;
import com.example.ucomandbackend.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ads")
@Entity
@Getter
@Setter
@ToString
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AdType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ad_id")
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private Set<AdCompetenceLevelTag> tags = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private MotivationType motivation;

    private int hoursPerWeek;

    private String freeLink;

    private String ownLink;

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
