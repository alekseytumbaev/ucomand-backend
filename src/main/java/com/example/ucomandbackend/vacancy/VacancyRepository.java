package com.example.ucomandbackend.vacancy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("SELECT v FROM Vacancy v JOIN FETCH v.tags t WHERE t.id IN :tagIds")
    Page<Vacancy> findAllByTagIdsIn(Pageable pageable, List<Long> tagIds);

    List<Vacancy> findAllByOwner_Id(Long id);
}