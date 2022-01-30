package com.ryhnik.service;

import com.ryhnik.dto.city.CityInputCreateDto;
import com.ryhnik.entity.City;
import com.ryhnik.entity.Region;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.EntityNotFoundException;
import com.ryhnik.repository.CityRepository;
import com.ryhnik.repository.RegionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final RegionRepository repository;

    public CityService(CityRepository cityRepository, RegionRepository repository) {
        this.cityRepository = cityRepository;
        this.repository = repository;
    }

    public City save(CityInputCreateDto createDto) {
        Region region = repository.findByName(createDto.getRegion())
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));

        City city = new City();
        city.setName(createDto.getName());
        city.setRegion(region);

        return cityRepository.save(city);
    }

    public City getById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));
    }

    public Page<City> getAll(String name, Pageable pageable) {
        return cityRepository.getAll(name, pageable);
    }
}
