package com.ryhnik.repository;

import com.ryhnik.entity.Master;
import com.ryhnik.entity.MasterCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface MasterRepository extends JpaRepository<Master, Long> {

    @Query("SELECT m FROM Master m WHERE m.category = :category AND m.startedAt <= :experience")
    Page<Master> findAll(MasterCategory category,
                         LocalDate experience,
                         Pageable pageable);

    @Query("SELECT m FROM Master m " +
            "JOIN m.user u " +
            "WHERE u.username = :username " +
            "AND m.id = :masterId")
    Optional<Master> findByUsernameAndMasterId(String username, Long masterId);

    @Query("SELECT m FROM Master m " +
            "JOIN m.user u " +
            "WHERE u.username = :username")
    Optional<Master> findMasterByUsername(String username);
}
