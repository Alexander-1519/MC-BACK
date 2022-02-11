package com.ryhnik.mapper;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.maintenance.MaintenanceInputCreateDto;
import com.ryhnik.dto.maintenance.MaintenanceOutputDto;
import com.ryhnik.entity.Maintenance;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(
        componentModel = "spring"
)
public interface MaintenanceMapper {

    Maintenance toMaintenance(MaintenanceInputCreateDto createDto);

    MaintenanceOutputDto toOutputDto(Maintenance maintenance);

    PageDto<MaintenanceOutputDto> toOutputDto(Page<Maintenance> maintenances, Pageable pageable);
}
