package com.ryhnik.controller;

import com.ryhnik.dto.master.MasterFullInputCreateDto;
import com.ryhnik.dto.master.MasterFullOutputDto;
import com.ryhnik.dto.master.MasterInputUpdateDto;
import com.ryhnik.dto.master.MasterOutputDto;
import com.ryhnik.entity.Master;
import com.ryhnik.mapper.MasterMapper;
import com.ryhnik.service.MasterService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/masters", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Master", description = "API for masters")
public class MasterController {

    private final MasterService masterService;
    private final MasterMapper masterMapper;

    public MasterController(MasterService masterService, MasterMapper masterMapper) {
        this.masterService = masterService;
        this.masterMapper = masterMapper;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MasterFullOutputDto> updateById(@PathVariable Long id,
                                                      @RequestPart(value = "createDto") MasterFullInputCreateDto createDto,
                                                      @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        Master master = masterService.updateInfo(id, masterMapper.toMaster(createDto), images);

        return ResponseEntity.status(HttpStatus.OK)
                .body(masterMapper.toFullOutputDto(master));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        masterService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasterFullOutputDto> getById(@PathVariable Long id) {
        Master master = masterService.findMasterById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(masterMapper.toFullOutputDto(master));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST,
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<MasterFullOutputDto> save(
            @PathVariable Long id,
            @RequestPart(value = "createDto") MasterFullInputCreateDto createDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                    Principal principal) {
        Master master = masterService.updateInfo(id, masterMapper.toMaster(createDto), images);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(masterMapper.toFullOutputDto(master));
    }
}
