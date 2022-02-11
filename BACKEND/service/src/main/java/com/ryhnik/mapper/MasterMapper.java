package com.ryhnik.mapper;

import com.ryhnik.dto.core.PageDto;
import com.ryhnik.dto.master.MasterInputUpdateDto;
import com.ryhnik.dto.master.MasterOutputDto;
import com.ryhnik.entity.Master;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(
        componentModel = "spring"
)
public interface MasterMapper {

    Master toMaster(MasterInputUpdateDto updateDto);

    MasterOutputDto toOutputDto(Master master);

    PageDto<MasterOutputDto> toPageDto(Page<Master> masters, Pageable pageable);
}
