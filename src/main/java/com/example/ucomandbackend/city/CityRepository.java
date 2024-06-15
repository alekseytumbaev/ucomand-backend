package com.example.ucomandbackend.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("""
            select c from City c
            join fetch c.region
            join fetch c.region.district
            """)
    List<City> findAllWithRegionAndDistrict();
}
