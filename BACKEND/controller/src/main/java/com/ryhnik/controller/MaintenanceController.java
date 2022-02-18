package com.ryhnik.controller;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.maintenance.MaintenanceInputCreateDto;
import com.ryhnik.dto.maintenance.MaintenanceOutputDto;
import com.ryhnik.entity.Maintenance;
import com.ryhnik.mapper.MaintenanceMapper;
import com.ryhnik.service.MaintenanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "api/v1/masters/{masterId}/maintenances", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Maintenances", description = "API for maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;
    private final MaintenanceMapper maintenanceMapper;

    public MaintenanceController(MaintenanceService maintenanceService,
                                 MaintenanceMapper maintenanceMapper) {
        this.maintenanceService = maintenanceService;
        this.maintenanceMapper = maintenanceMapper;
    }

    @PostMapping
    public ResponseEntity<MaintenanceOutputDto> create(@PathVariable Long masterId,
                                                       @RequestBody @Valid MaintenanceInputCreateDto createDto,
                                                       Principal principal){
        Maintenance maintenance = maintenanceService.create(
                principal.getName(),
                masterId,
                maintenanceMapper.toMaintenance(createDto));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(maintenanceMapper.toOutputDto(maintenance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long masterId, @PathVariable Long id){
        maintenanceService.deleteById(masterId, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<PageDto<MaintenanceOutputDto>> findAll(@PathVariable Long masterId, Pageable pageable){
        Page<Maintenance> maintenances = maintenanceService.findAll(masterId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(maintenanceMapper.toOutputDto(maintenances, maintenances.getPageable()));
    }
}
