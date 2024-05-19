package com.example.ucomandbackend.project;

import com.example.ucomandbackend.project.dto.ProjectStage;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.vacancy.Vacancy;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private ProjectStage stage;

    private String fieldOfWork;

    @Column
    private String description;

    @Column
    private String freeLink;

    @Column
    private String ownLink;

    @Schema(description = "Что уже сделано?")
    @Column
    private String whatAlreadyDone;

    @Column
    private String goals;

    @ManyToMany
    @JoinTable(name = "projects_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private Set<User> command;

    @Column
    private String projectNews;

    @ManyToMany
    @JoinTable(name = "projects_vacancies",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id"))
    @ToString.Exclude
    private Set<Vacancy> vacancies;

    @Column
    private OffsetDateTime creationDate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Project project = (Project) o;
        return getId() != null && Objects.equals(getId(), project.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
