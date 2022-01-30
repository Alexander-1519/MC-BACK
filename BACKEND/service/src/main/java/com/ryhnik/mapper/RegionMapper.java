package com.ryhnik.mapper;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.region.RegionInputCreateDto;
import com.ryhnik.dto.region.RegionOutputDto;
import com.ryhnik.entity.Region;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    RegionOutputDto toRegionOutputDto(Region region);

    Region toRegion(RegionInputCreateDto createDto);

    PageDto<RegionOutputDto> toPagedRegionOutputDto(Page<Region> regions, Pageable pageable);
}
