package com.example.ucomandbackend.ad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad, Long>, JpaSpecificationExecutor<Ad> {

    List<Ad> findAllByTypeAndUser_Id(AdType adType, long userId);

    Optional<Ad> findByTypeAndId(AdType adType, long id);

    void deleteByTypeAndId(AdType adType, long id);
}