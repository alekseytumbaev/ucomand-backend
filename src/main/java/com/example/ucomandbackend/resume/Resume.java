package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.resume.dto.MotivationType;
import com.example.ucomandbackend.resume.dto.VisibilityLevel;
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
@Table(name = "resumes")
@Entity
@Getter
@Setter
@ToString
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //TODO уровень видимости

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private MotivationType motivation;

    private int hoursPerWeek;

    private String freeLink;

    private String ownLink;

    private String details;

    @Enumerated(EnumType.STRING)
    private VisibilityLevel visibility;

    private OffsetDateTime creationDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resume_id")
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private Set<ResumeCompetenceLevelTag> tags = new HashSet<>();

    public boolean addTag(ResumeCompetenceLevelTag tag) {
        boolean isModified = tags.add(tag);
        tag.setResume(this);
        return isModified;
    }

    public boolean addTags(Set<ResumeCompetenceLevelTag> tags) {
        boolean isModified = this.tags.addAll(tags);
        for (ResumeCompetenceLevelTag tag : tags) {
            tag.setResume(this);
        }
        return isModified;
    }

    @Nullable
    public ResumeCompetenceLevelTag getProfession() {
        return tags.stream()
                .filter(tag -> tag.getTag().getType() == TagType.PROFESSION)
                .findFirst()
                .orElse(null);
    }

    public Set<ResumeCompetenceLevelTag> getSkills() {
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
        Resume resume = (Resume) o;
        return getId() != null && Objects.equals(getId(), resume.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
