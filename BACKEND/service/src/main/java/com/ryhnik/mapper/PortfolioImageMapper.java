package com.ryhnik.mapper;

import com.ryhnik.dto.portfolioimage.PortfolioImageOutputDto;
import com.ryhnik.entity.PortfolioImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface PortfolioImageMapper {

    @Mapping(target = "masterId", source = "master.id")
    PortfolioImageOutputDto toPortfolioOutputDto(PortfolioImage portfolioImage);

    List<PortfolioImageOutputDto> toPortfolioOutputDto(List<PortfolioImage> portfolioImages);
}
