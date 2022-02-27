package com.ryhnik.service;

import com.ryhnik.entity.MaintenanceDate;
import com.ryhnik.entity.Master;
import com.ryhnik.exception.*;
import com.ryhnik.repository.MaintenanceDateRepository;
import com.ryhnik.repository.MaintenanceRepository;
import com.ryhnik.repository.MasterRepository;
import com.ryhnik.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceDateService {

    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceDateRepository maintenanceDateRepository;
    private final UserRepository userRepository;
    private final MasterRepository masterRepository;

    public MaintenanceDateService(MaintenanceRepository maintenanceRepository,
                                  MaintenanceDateRepository maintenanceDateRepository,
                                  UserRepository userRepository,
                                  MasterRepository masterRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.maintenanceDateRepository = maintenanceDateRepository;
        this.userRepository = userRepository;
        this.masterRepository = masterRepository;
    }

    public Master create(List<MaintenanceDate> dates, String username) {
        Master master = masterRepository.findMasterByUsername(username)
                .orElseThrow(() -> new NoSuchMasterException(username));

        List<MaintenanceDate> maintenanceDates = maintenanceDateRepository.saveAll(dates);

        master.setDates(maintenanceDates);
        return masterRepository.save(master);
    }

    public List<MaintenanceDate> update(List<MaintenanceDate> updateDate, String username) {
        List<MaintenanceDate> maintenanceDates = maintenanceDateRepository.findByUsername(username)
                .orElseThrow(() -> ExceptionBuilder.builder(Code.MAINTENANCE_DATE_EXCEPTION)
                        .withMessage("Can't find maintenanses by username = " + username)
                        .build(MasterClubException.class));

        for (MaintenanceDate date : maintenanceDates) {
            date.setDate(updateDate.stream()
                    .filter(d -> d.getId().equals(date.getId()))
                    .findFirst()
                    .get().getDate());
        }

        return maintenanceDateRepository.saveAll(maintenanceDates);
    }

    public void deleteById(Long dateId, String username) {
        MaintenanceDate maintenanceDate = maintenanceDateRepository.findByIdAndUsername(dateId, username)
                .orElseThrow(() -> new NoSuchMaintenanceDateException(dateId));

        maintenanceDateRepository.delete(maintenanceDate);
    }

    public Page<MaintenanceDate> findAll(String username, Pageable pageable) {
        return maintenanceDateRepository.findAll(username, pageable);
    }

    public List<MaintenanceDate> getAllByUserId(Long id){
        return maintenanceDateRepository.findByUserId(id);
    }

    public List<MaintenanceDate> createAll(List<MaintenanceDate> dates) {
        return maintenanceDateRepository.saveAll(dates);
    }
}