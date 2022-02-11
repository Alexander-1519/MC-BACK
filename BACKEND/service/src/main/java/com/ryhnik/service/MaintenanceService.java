package com.ryhnik.service;

import com.ryhnik.entity.Maintenance;
import com.ryhnik.entity.Master;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.EntityNotFoundException;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.repository.MaintenanceRepository;
import com.ryhnik.repository.MasterRepository;
import com.ryhnik.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final UserRepository userRepository;
    private final MasterRepository masterRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository,
                              UserRepository userRepository,
                              MasterRepository masterRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.userRepository = userRepository;
        this.masterRepository = masterRepository;
    }

    public Maintenance create(String username, Long masterId, Maintenance maintenance) {
        Master master = masterRepository.findByUsernameAndMasterId(username, masterId)
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));

        maintenance.setMaster(master);
        return maintenanceRepository.save(maintenance);
    }

    public void deleteById(Long masterId, Long maintenanceId) {
        boolean existsByIds = maintenanceRepository.existsByMasterIdAndMaintenanceId(masterId, maintenanceId);
        if (!existsByIds) {
            throw ExceptionBuilder.builder(Code.UNEXPECTED)
                    .withMessage("Can't find maintenance on this master")
                    .build(MasterClubException.class);
        }

        maintenanceRepository.deleteById(maintenanceId);
    }

    public Page<Maintenance> findAll(Long masterId, Pageable pageable) {
        return maintenanceRepository.findAll(masterId, pageable);
    }
}
