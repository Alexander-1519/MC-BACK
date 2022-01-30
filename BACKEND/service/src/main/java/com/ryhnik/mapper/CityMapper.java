package com.ryhnik.mapper;

import com.ryhnik.dto.city.CityOutputDto;
import com.ryhnik.dto.core.PageDto;
import com.ryhnik.entity.City;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityOutputDto toCityOutputDto(City city);

    PageDto<CityOutputDto> toPagedCityOutputDto(Page<City> cities, Pageable pageable);
}
