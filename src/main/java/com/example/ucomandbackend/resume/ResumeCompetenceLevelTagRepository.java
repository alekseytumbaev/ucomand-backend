package com.example.ucomandbackend.resume;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeCompetenceLevelTagRepository extends JpaRepository<ResumeCompetenceLevelTag, Long> {
    void deleteByResume_Id(Long resumeId);
}