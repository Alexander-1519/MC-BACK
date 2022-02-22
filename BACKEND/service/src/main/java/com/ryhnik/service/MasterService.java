package com.ryhnik.service;

import com.ryhnik.dto.master.filter.MasterFilterDto;
import com.ryhnik.entity.Maintenance;
import com.ryhnik.entity.MaintenanceDate;
import com.ryhnik.entity.Master;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.exception.NoSuchMasterException;
import com.ryhnik.repository.MasterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterService {

    private final MasterRepository masterRepository;
    private final MaintenanceService maintenanceService;
    private final MaintenanceDateService maintenanceDateService;
    private final MasterReviewService masterReviewService;
    private final PortfolioImageService portfolioImageService;

    public MasterService(MasterRepository masterRepository,
                         MaintenanceService maintenanceService,
                         MaintenanceDateService maintenanceDateService,
                         MasterReviewService masterReviewService,
                         PortfolioImageService portfolioImageService) {
        this.masterRepository = masterRepository;
        this.maintenanceService = maintenanceService;
        this.maintenanceDateService = maintenanceDateService;
        this.masterReviewService = masterReviewService;
        this.portfolioImageService = portfolioImageService;
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

    public Master saveMasterInfo(Master createMaster, List<MultipartFile> images, String username, Long masterId) {
        Master master = masterRepository.findMasterByUsername(username)
                .orElseThrow(() -> new NoSuchMasterException(username));

        master.setInfo(createMaster.getInfo());
        master.setStartedAt(createMaster.getStartedAt());

        List<Maintenance> maintenances = createMaster.getMaintenances();
        maintenances.stream().filter(m -> m.getId() == null).map(m -> {
            return maintenanceService.create(username, masterId, m);
        });

        List<MaintenanceDate> dates = createMaster.getDates();
        List<MaintenanceDate> dateList = dates.stream().filter(d -> d.getId() == null).collect(Collectors.toList());
        maintenanceDateService.create(dateList, username);

        portfolioImageService.create(images, username);

        return masterRepository.save(master);
    }

    public Master getById(Long id) {
        return masterRepository.findById(id)
                .orElseThrow(() -> new NoSuchMasterException(id));
    }
}
