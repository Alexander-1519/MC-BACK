package com.ryhnik.controller;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.maintenancedate.MaintenanceDateInputCreateDto;
import com.ryhnik.dto.maintenancedate.MaintenanceDateOutputDto;
import com.ryhnik.dto.master.MasterOutputDto;
import com.ryhnik.entity.MaintenanceDate;
import com.ryhnik.entity.Master;
import com.ryhnik.mapper.MaintenanceDateMapper;
import com.ryhnik.mapper.MasterMapper;
import com.ryhnik.service.MaintenanceDateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/masters/{masterId}/maintenance-dates", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "MaintenanceDates", description = "API for maintenance dates")
public class MaintenanceDateController {

    private final MaintenanceDateService maintenanceDateService;
    private final MasterMapper masterMapper;
    private final MaintenanceDateMapper dateMapper;

    public MaintenanceDateController(MaintenanceDateService maintenanceDateService,
                                     MasterMapper masterMapper,
                                     MaintenanceDateMapper dateMapper) {
        this.maintenanceDateService = maintenanceDateService;
        this.masterMapper = masterMapper;
        this.dateMapper = dateMapper;
    }

    @GetMapping
    public ResponseEntity<PageDto<MaintenanceDateOutputDto>> findAll(Principal principal, Pageable pageable) {
        Page<MaintenanceDate> all = maintenanceDateService.findAll(principal.getName(), pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(dateMapper.toMaintenanceOutputDto(all, all.getPageable()));
    }

    @PostMapping
    public ResponseEntity<MasterOutputDto> create(@RequestBody List<MaintenanceDateInputCreateDto> createDto,
                                                  Principal principal) {
        Master master = maintenanceDateService.create(dateMapper.toMaintenanceDate(createDto), principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(masterMapper.toOutputDto(master));
    }

    @PutMapping
    public ResponseEntity<List<MaintenanceDateOutputDto>> update(@RequestBody List<MaintenanceDateInputCreateDto> updateDto,
                                                  Principal principal) {
        List<MaintenanceDate> update = maintenanceDateService.update(dateMapper.toMaintenanceDate(updateDto),
                principal.getName());

        return ResponseEntity.status(HttpStatus.OK)
                .body(dateMapper.toMaintenanceOutputDto(update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, Principal principal) {
        maintenanceDateService.deleteById(id, principal.getName());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
