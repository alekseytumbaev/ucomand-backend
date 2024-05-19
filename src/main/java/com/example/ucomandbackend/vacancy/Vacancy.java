package com.example.ucomandbackend.vacancy;

import com.example.ucomandbackend.project.Project;
import com.example.ucomandbackend.resume.dto.MotivationType;
import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
@Entity
@Getter
@Setter
@ToString
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Tag profession;

    @ManyToMany
    @JoinTable(name = "vacancies_tags",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ToString.Exclude
    private Set<Tag> tags;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private MotivationType payment;

    @Column
    private OffsetDateTime creationDate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Vacancy vacancy = (Vacancy) o;
        return getId() != null && Objects.equals(getId(), vacancy.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
