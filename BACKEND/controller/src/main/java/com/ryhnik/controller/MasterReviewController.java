package com.ryhnik.controller;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.masterreview.MasterReviewInputCreateDto;
import com.ryhnik.dto.masterreview.MasterReviewOutputDto;
import com.ryhnik.entity.MasterReview;
import com.ryhnik.mapper.MasterReviewMapper;
import com.ryhnik.service.MasterReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "api/v1/masters/{masterId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "MasterReviews", description = "API for master reviews")
public class MasterReviewController {

    private final MasterReviewMapper masterReviewMapper;
    private final MasterReviewService masterReviewService;

    public MasterReviewController(MasterReviewMapper masterReviewMapper, MasterReviewService masterReviewService) {
        this.masterReviewMapper = masterReviewMapper;
        this.masterReviewService = masterReviewService;
    }

    @PostMapping
    public ResponseEntity<MasterReviewOutputDto> create(@PathVariable Long masterId,
                                                        @RequestBody MasterReviewInputCreateDto createDto,
                                                        Principal principal) {
        MasterReview masterReview = masterReviewService.create(masterReviewMapper.toMasterReview(createDto),
                principal.getName(), masterId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(masterReviewMapper.toOutputDto(masterReview));
    }

    @GetMapping
    public ResponseEntity<PageDto<MasterReviewOutputDto>> findAllByMasterId(@PathVariable Long masterId,
                                                                            Pageable pageable,
                                                                            Principal principal) {
        Page<MasterReview> reviews = masterReviewService.getAll(masterId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(masterReviewMapper.toMasterReviewOutputDto(reviews, reviews.getPageable()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MasterReviewOutputDto> updateById(@PathVariable Long masterId,
                                                            @PathVariable Long id,
                                                            @RequestBody MasterReviewInputCreateDto createDto,
                                                            Principal principal) {
        MasterReview masterReview = masterReviewService.updateMasterReview(id, principal.getName(),
                masterReviewMapper.toMasterReview(createDto));

        return ResponseEntity.status(HttpStatus.OK)
                .body(masterReviewMapper.toOutputDto(masterReview));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long masterId,
                                           @PathVariable Long id,
                                           Principal principal) {
        masterReviewService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}