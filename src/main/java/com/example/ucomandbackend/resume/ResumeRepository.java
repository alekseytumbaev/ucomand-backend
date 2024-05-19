package com.example.ucomandbackend.resume;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findAllByUser_Id(long userId);

    @Query("SELECT r FROM Resume r JOIN FETCH r.tags t WHERE t.id IN :tagIds")
    Page<Resume> findAllByTagIdsIn(Pageable pageable, List<Long> tagIds);
}