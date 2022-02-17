package com.ryhnik.service;

import com.ryhnik.dto.master.filter.MasterFilterDto;
import com.ryhnik.entity.Master;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.exception.NoSuchMasterException;
import com.ryhnik.repository.MasterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MasterService {

    private final MasterRepository masterRepository;

    public MasterService(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public Page<Master> findAll(MasterFilterDto filter, Pageable pageable) {
        return masterRepository.findAll(filter.getCategory(), LocalDate.ofEpochDay(filter.getExperience()), pageable);
    }

    public Master updateById(Long masterId, Master masterUpd) {
        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new NoSuchMasterException(masterId));

        master.setInfo(masterUpd.getInfo());

        return masterRepository.save(master);
    }

    public void deleteById(Long masterId) {
        boolean existsById = masterRepository.existsById(masterId);
        if (!existsById) {
            throw ExceptionBuilder.builder(Code.MASTER_EXCEPTION)
                    .withMessage("Can't find master with id = " + masterId)
                    .build(MasterClubException.class);
        }

        masterRepository.deleteById(masterId);
    }
}
