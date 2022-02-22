package com.ryhnik.mapper;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.master.MasterFullInputCreateDto;
import com.ryhnik.dto.master.MasterFullOutputDto;
import com.ryhnik.dto.master.MasterInputUpdateDto;
import com.ryhnik.dto.master.MasterOutputDto;
import com.ryhnik.entity.Master;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(
        componentModel = "spring",
        uses = {MaintenanceMapper.class,
                MaintenanceDateMapper.class,
                PortfolioImageMapper.class,
                MasterReviewMapper.class,
                UserMapper.class
        }
)
public interface MasterMapper {

    Master toMaster(MasterInputUpdateDto updateDto);

    MasterOutputDto toOutputDto(Master master);

    PageDto<MasterOutputDto> toPageDto(Page<Master> masters, Pageable pageable);

    MasterFullOutputDto toFullOutputDto(Master master);

    @Mapping(target = "images", ignore = true)
    Master toMaster(MasterFullInputCreateDto inputCreateDto);
}
