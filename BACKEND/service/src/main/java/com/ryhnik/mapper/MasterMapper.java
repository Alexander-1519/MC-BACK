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

//    @Mapping(target = "startedAt", expression = "java(master.getStartedAt().toEpochSecond(java.time.LocalTime.MAX, java.time.ZoneOffset.UTC))")
    @Mapping(target = "startedAt", ignore = true)
    @Mapping(target = "username", source = "user.username")
    MasterFullOutputDto toFullOutputDto(Master master);

//    @Mapping(target = "startedAt", expression = "java(java.time.LocalDate.ofInstant(java.time.Instant.ofEpochMilli(inputCreateDto.getStartedAt()), java.util.TimeZone.getDefault().toZoneId()))")
    @Mapping(target = "startedAt", ignore = true)
    Master toMaster(MasterFullInputCreateDto inputCreateDto);
}
