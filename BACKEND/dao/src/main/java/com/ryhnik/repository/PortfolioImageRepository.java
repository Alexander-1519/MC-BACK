package com.ryhnik.repository;

import com.ryhnik.entity.PortfolioImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortfolioImageRepository extends JpaRepository<PortfolioImage, Long> {

    List<PortfolioImage> getAllByMasterId(Long masterId);
}
