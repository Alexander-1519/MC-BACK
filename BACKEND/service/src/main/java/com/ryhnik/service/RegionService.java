package com.ryhnik.service;

import com.ryhnik.entity.Region;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.exception.NoSuchRegionException;
import com.ryhnik.repository.RegionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public Region save(Region region) {
        if (regionRepository.existsByName(region.getName())) {
            throw ExceptionBuilder.builder(Code.REGION_NOT_FOUND)
                    .withMessage("Region name is already exists")
                    .build(MasterClubException.class);
        }

        return regionRepository.save(region);
    }

    public Region getById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new NoSuchRegionException(id));
    }

    public Region getByName(String name) {
        return regionRepository.findByName(name)
                .orElseThrow(() -> ExceptionBuilder.builder(Code.REGION_NOT_FOUND)
                        .withMessage("Can't find region with name = " + name)
                        .build(MasterClubException.class));
    }

    public Page<Region> findAll(String name, Pageable pageable) {
        return regionRepository.findAll(name, pageable);
    }
}
