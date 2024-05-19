package com.example.ucomandbackend.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p JOIN FETCH p.command t WHERE t.id = :userId")
    List<Project> findAllByUserId(Long userId);
}