package com.ryhnik.controller;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.region.RegionInputCreateDto;
import com.ryhnik.dto.region.RegionOutputDto;
import com.ryhnik.entity.Region;
import com.ryhnik.mapper.RegionMapper;
import com.ryhnik.service.RegionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/regions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Regions", description = "API for regions")
public class RegionController {

    private final RegionService regionService;
    private final RegionMapper regionMapper;

    public RegionController(RegionService regionService, RegionMapper regionMapper) {
        this.regionService = regionService;
        this.regionMapper = regionMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RegionOutputDto> save(@RequestBody RegionInputCreateDto createDto) {
        Region region = regionService.save(regionMapper.toRegion(createDto));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(regionMapper.toRegionOutputDto(region));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionOutputDto> getById(@PathVariable Long id) {
        Region region = regionService.getById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(regionMapper.toRegionOutputDto(region));
    }

    @GetMapping
    public ResponseEntity<PageDto<RegionOutputDto>> getAll(@RequestParam(required = false, defaultValue = "") String name,
                                                           Pageable pageable) {
        Page<Region> regions = regionService.findAll(name, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(regionMapper.toPagedRegionOutputDto(regions, regions.getPageable()));
    }
}
