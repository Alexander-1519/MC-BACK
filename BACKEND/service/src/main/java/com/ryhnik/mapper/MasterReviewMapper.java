package com.ryhnik.mapper;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.masterreview.MasterReviewInputCreateDto;
import com.ryhnik.dto.masterreview.MasterReviewOutputDto;
import com.ryhnik.entity.MasterReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface MasterReviewMapper {

    @Mapping(target = "masterId", source = "master.id")
    @Mapping(target = "imageUrl", source = "user.imageUrl")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    MasterReviewOutputDto toOutputDto(MasterReview masterReview);

    MasterReview toMasterReview(MasterReviewInputCreateDto createDto);

    PageDto<MasterReviewOutputDto> toMasterReviewOutputDto(Page<MasterReview> masterReviews, Pageable pageable);
}
