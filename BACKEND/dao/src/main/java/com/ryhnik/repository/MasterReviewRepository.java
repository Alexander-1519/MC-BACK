package com.ryhnik.repository;

import com.ryhnik.entity.MasterReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MasterReviewRepository extends JpaRepository<MasterReview, Long> {

    Page<MasterReview> findAllByMasterId(Long masterId, Pageable pageable);

    Optional<MasterReview> findByIdAndUserUsername(Long id, String username);

    @Query("SELECT mr FROM MasterReview mr WHERE mr.master.user.id = :userId")
    List<MasterReview> findAllByUserId(Long userId);
}
