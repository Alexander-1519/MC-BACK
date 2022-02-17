package com.ryhnik.repository;

import com.ryhnik.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    boolean existsByName(String name);

    Optional<Region> findByName(String name);

    @Query("SELECT r FROM Region r WHERE lower(r.name) LIKE lower(concat('%', :name, '%'))")
    Page<Region> findAll(String name, Pageable pageable);
}
