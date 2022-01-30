package com.ryhnik.controller;

import com.ryhnik.dto.city.CityInputCreateDto;
import com.ryhnik.dto.city.CityOutputDto;
import com.ryhnik.dto.core.PageDto;
import com.ryhnik.entity.City;
import com.ryhnik.mapper.CityMapper;
import com.ryhnik.service.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/cities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Cities", description = "API for cities")
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CityOutputDto> save(@RequestBody CityInputCreateDto createDto){
        City city = cityService.save(createDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cityMapper.toCityOutputDto(city));
    }

    @GetMapping
    public ResponseEntity<PageDto<CityOutputDto>> getAll(@RequestParam(required = false, defaultValue = "") String name,
                                                         Pageable pageable){
        Page<City> cities = cityService.getAll(name, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(cityMapper.toPagedCityOutputDto(cities, cities.getPageable()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityOutputDto> getById(@PathVariable Long id){
        City city = cityService.getById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(cityMapper.toCityOutputDto(city));
    }
}
