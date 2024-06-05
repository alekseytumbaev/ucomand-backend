package com.example.ucomandbackend.ad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAllByTypeAndUser_Id(AdType adType, long userId);

    Optional<Ad> findByTypeAndId(AdType adType, long id);

    Page<Ad> findAllByType(Pageable pageable, AdType adType);

    void deleteByTypeAndId(AdType adType, long id);
}