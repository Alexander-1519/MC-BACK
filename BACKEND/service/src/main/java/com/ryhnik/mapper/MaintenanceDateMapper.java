package com.ryhnik.mapper;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.maintenancedate.MaintenanceDateInputCreateDto;
import com.ryhnik.dto.maintenancedate.MaintenanceDateOutputDto;
import com.ryhnik.entity.MaintenanceDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface MaintenanceDateMapper {

    List<MaintenanceDate> toMaintenanceDate(List<MaintenanceDateInputCreateDto> inputCreateDtos);

    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.ofInstant(java.time.Instant." +
            "ofEpochMilli(inputCreateDto.getDate()), java.util.TimeZone.getDefault().toZoneId()))")
    MaintenanceDate toMaintenanceDate(MaintenanceDateInputCreateDto inputCreateDto);

    PageDto<MaintenanceDateOutputDto> toMaintenanceOutputDto(Page<MaintenanceDate> maintenanceDates, Pageable pageable);

    @Mapping(target = "date", expression = "java(java.time.ZonedDateTime.of(maintenanceDate.getDate(), " +
            "java.time.ZoneId.systemDefault()).toEpochSecond())")
    MaintenanceDateOutputDto toMaintenanceOutputDto(MaintenanceDate maintenanceDate);

    List<MaintenanceDateOutputDto> toMaintenanceOutputDto(List<MaintenanceDate> maintenanceDates);
}
