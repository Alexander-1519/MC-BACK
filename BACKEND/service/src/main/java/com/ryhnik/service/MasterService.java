package com.ryhnik.service;

import com.ryhnik.dto.master.filter.MasterFilterDto;
import com.ryhnik.entity.*;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.exception.NoSuchMasterException;
import com.ryhnik.repository.MasterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

        List<Maintenance> maintenances = createMaster.getMaintenances().stream()
                .peek(m -> m.setMaster(master)).collect(Collectors.toList());
        maintenanceService.createAll(maintenances);

        List<MaintenanceDate> dates = createMaster.getDates().stream()
                .peek(m -> m.setMaster(master)).collect(Collectors.toList());
        maintenanceDateService.createAll(dates);

        portfolioImageService.create(images, username);

        Master savedMaster = masterRepository.save(master);
        return getById(savedMaster.getId());
    }

    public Master getById(Long userId) {
        Master master = masterRepository.findById(userId)
                .orElseThrow(() -> new NoSuchMasterException(userId));

        List<PortfolioImage> allImagesByMasterId = portfolioImageService.getAllImagesByMasterId(userId);
        master.setImages(allImagesByMasterId);

        List<MasterReview> reviews = masterReviewService.getAllByUserId(userId);
        master.setReviews(reviews);

        List<MaintenanceDate> maintenanceDates = maintenanceDateService.getAllByUserId(userId);
        master.setDates(maintenanceDates);

        List<Maintenance> maintenances = maintenanceService.getAllByUserId(userId);
        master.setMaintenances(maintenances);

        return master;
    }

    public void simpleSaveAllData() {

    }
}
