package com.example.ucomandbackend.resume;

import com.example.ucomandbackend.resume.dto.MotivationType;
import com.example.ucomandbackend.tags.Tag;
import com.example.ucomandbackend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private MotivationType motivation;

    private int hoursPerWeek;

    private String freeLink;

    private String ownLink;

    private String details;

    private OffsetDateTime creationDate;

    @ManyToMany
    @JoinTable(name = "resumes_tags",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ToString.Exclude
    private Set<ResumeCompetenceLevelTag> tags;
}
