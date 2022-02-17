package com.ryhnik.service;

import com.ryhnik.dto.city.CityInputCreateDto;
import com.ryhnik.entity.City;
import com.ryhnik.entity.Region;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.exception.NoSuchCityException;
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
                .orElseThrow(() -> ExceptionBuilder.builder(Code.CITY_EXCEPTION)
                        .withMessage("Can't find city with name = " + createDto.getRegion())
                        .build(MasterClubException.class));

        City city = new City();
        city.setName(createDto.getName());
        city.setRegion(region);

        return cityRepository.save(city);
    }

    public City getById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new NoSuchCityException(id));
    }

    public Page<City> getAll(String name, Pageable pageable) {
        return cityRepository.getAll(name, pageable);
    }
}
