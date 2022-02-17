package com.ryhnik.repository;

import com.ryhnik.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE lower(c.name) LIKE lower(concat('%', :name, '%'))")
    Page<City> getAll(String name, Pageable pageable);
}
