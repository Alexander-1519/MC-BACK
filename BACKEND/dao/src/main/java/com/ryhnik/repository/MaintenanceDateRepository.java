package com.ryhnik.repository;

import com.ryhnik.entity.MaintenanceDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MaintenanceDateRepository extends JpaRepository<MaintenanceDate, Long> {

    @Query("SELECT md FROM MaintenanceDate md " +
            "JOIN md.master m " +
            "JOIN m.user u " +
            "WHERE u.username = :username")
    Optional<List<MaintenanceDate>> findByUsername(String username);

    @Query("SELECT md FROM MaintenanceDate md " +
            "JOIN md.master m " +
            "JOIN m.user u " +
            "WHERE u.username = :username AND md.id = :id")
    Optional<MaintenanceDate> findByIdAndUsername(Long id, String username);

    @Query("SELECT md FROM MaintenanceDate md " +
            "JOIN md.master.user u " +
            "WHERE u.username = :username")
    Page<MaintenanceDate> findAll(String username, Pageable pageable);

    @Query("SELECT md FROM MaintenanceDate md WHERE md.master.user.id = :id")
    List<MaintenanceDate> findByUserId(Long id);
}
