package com.example.ucomandbackend.vacancy;

import com.example.ucomandbackend.resume.ResumeCompetenceLevelTag;
import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.tags.dto.CompetenceLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacancy_competence_level_tags")
@Entity
@Getter
@Setter
@ToString
public class VacancyCompetenceLevelTag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CompetenceLevel competenceLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id")
    @ToString.Exclude
    private Vacancy vacancy;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ResumeCompetenceLevelTag that = (ResumeCompetenceLevelTag) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
