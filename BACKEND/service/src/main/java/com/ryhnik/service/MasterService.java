package com.ryhnik.service;

import com.ryhnik.dto.master.MasterFullInputCreateDto;
import com.ryhnik.dto.master.filter.MasterFilterDto;
import com.ryhnik.entity.*;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.ExceptionBuilder;
import com.ryhnik.exception.MasterClubException;
import com.ryhnik.exception.NoSuchMasterException;
import com.ryhnik.repository.MaintenanceDateRepository;
import com.ryhnik.repository.MaintenanceRepository;
import com.ryhnik.repository.MasterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterService {

    private final MasterRepository masterRepository;
    private final MaintenanceService maintenanceService;
    private final MaintenanceDateService maintenanceDateService;
    private final MasterReviewService masterReviewService;
    private final PortfolioImageService portfolioImageService;
    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceDateRepository maintenanceDateRepository;

    public MasterService(MasterRepository masterRepository,
                         MaintenanceService maintenanceService,
                         MaintenanceDateService maintenanceDateService,
                         MasterReviewService masterReviewService,
                         PortfolioImageService portfolioImageService,
                         MaintenanceRepository maintenanceRepository,
                         MaintenanceDateRepository maintenanceDateRepository) {
        this.masterRepository = masterRepository;
        this.maintenanceService = maintenanceService;
        this.maintenanceDateService = maintenanceDateService;
        this.masterReviewService = masterReviewService;
        this.portfolioImageService = portfolioImageService;
        this.maintenanceRepository = maintenanceRepository;
        this.maintenanceDateRepository = maintenanceDateRepository;
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

    public Master saveAll(Master createMaster, List<MultipartFile> images, String username, Long masterId) {
        Master master = masterRepository.findMasterByUsername(username)
                .orElseThrow(() -> new NoSuchMasterException(username));

        master.setInfo(createMaster.getInfo());
        master.setStartedAt(createMaster.getStartedAt());

        List<Maintenance> maintenancesFromDb = maintenanceRepository.findAllByUserId(master.getUser().getId());
        List<Maintenance> maintenancesToUpdate = createMaster.getMaintenances();
        maintenancesToUpdate.forEach(m -> m.setMaster(master));

        List<Maintenance> maintenances = maintenanceRepository.saveAll(maintenancesToUpdate);

        for (Maintenance maintenanceFromDb : maintenancesFromDb) {
            long count = maintenancesToUpdate.stream().filter(m -> m.getId().equals(maintenanceFromDb.getId())).count();
            if (count == 0) {
                maintenanceRepository.delete(maintenanceFromDb);
            }
        }

        List<MaintenanceDate> datesFromDb = maintenanceDateRepository.findByUserId(master.getUser().getId());
        List<MaintenanceDate> datesToUpdate = createMaster.getDates();
        datesToUpdate.forEach(d -> d.setMaster(master));

        List<MaintenanceDate> maintenanceDates = maintenanceDateRepository.saveAll(datesToUpdate);

        for (MaintenanceDate date : datesFromDb) {
            long count = datesToUpdate.stream().filter(m -> m.getId().equals(date.getId())).count();
            if (count == 0) {
                maintenanceDateRepository.delete(date);
            }
        }

        List<PortfolioImage> imagesToUpdate = createMaster.getImages();
        List<PortfolioImage> imagesFromDb = portfolioImageService.getAllImagesByMasterId(masterId);
        imagesToUpdate.forEach(i -> i.setMaster(master));

        List<Long> idsToDelete = new ArrayList<>();

        for(PortfolioImage image: imagesFromDb) {
            long count = imagesToUpdate.stream().filter(i -> i.getId().equals(image.getId())).count();
            if(count == 0) {
                idsToDelete.add(image.getId());
            }
        }

        if(!idsToDelete.isEmpty()){
            portfolioImageService.deleteByIds(idsToDelete);
        }

        if(images != null && !images.isEmpty()) {
            portfolioImageService.create(images, username);
        }

        return getById(masterRepository.save(master).getId());
    }

    public Master saveAllMasterInfo(Master createMaster, List<MultipartFile> images, String username, Long masterId) {
        Master master = masterRepository.findMasterByUsername(username)
                .orElseThrow(() -> new NoSuchMasterException(username));

        master.setInfo(createMaster.getInfo());
        master.setStartedAt(createMaster.getStartedAt());

        List<Maintenance> maintenancesFromDb = maintenanceRepository.findAllByUserId(master.getUser().getId());
        List<Maintenance> maintenancesToUpdate = createMaster.getMaintenances();
        maintenancesToUpdate.forEach(m -> m.setMaster(master));

        List<Maintenance> maintenances = maintenanceRepository.saveAll(maintenancesToUpdate);

        for (Maintenance maintenanceFromDb : maintenancesFromDb) {
            long count = maintenancesToUpdate.stream().filter(m -> m.getId().equals(maintenanceFromDb.getId())).count();
            if (count > 0) {
                maintenanceRepository.delete(maintenanceFromDb);
            }
        }

        List<MaintenanceDate> datesFromDb = maintenanceDateRepository.findByUserId(master.getUser().getId());
        List<MaintenanceDate> datesToUpdate = createMaster.getDates();
        datesToUpdate.forEach(d -> d.setMaster(master));

        List<MaintenanceDate> maintenanceDates = maintenanceDateRepository.saveAll(datesToUpdate);

        for (MaintenanceDate date : datesFromDb) {
            long count = datesToUpdate.stream().filter(m -> m.getId().equals(date.getId())).count();
            if (count > 0) {
                maintenanceDateRepository.delete(date);
            }
        }

        List<PortfolioImage> imagesToUpdate = createMaster.getImages();
        List<PortfolioImage> imagesFromDb = portfolioImageService.getAllImagesByMasterId(masterId);
        imagesToUpdate.forEach(i -> i.setMaster(master));

        List<Long> idsToDelete = new ArrayList<>();

        for(PortfolioImage image: imagesFromDb) {
            long count = imagesToUpdate.stream().filter(i -> i.getId().equals(image.getId())).count();
            if(count >
                    0) {
                idsToDelete.add(image.getId());
            }
        }

        if(!idsToDelete.isEmpty()){
            portfolioImageService.deleteByIds(idsToDelete);
        }

        if(images != null && !images.isEmpty()) {
            portfolioImageService.create(images, username);
        }

        return getById(masterRepository.save(master).getId());
    }

    //TODO set startedAt
    public Master updateInfo(Long masterId, Master updatedMaster, List<MultipartFile> images) {
        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new NoSuchMasterException(masterId));

        updatedMasterInfo(master, updatedMaster);

        for(Maintenance maintenance: master.getMaintenances()){
            maintenance.setMaster(master);
        }
        for(MaintenanceDate date: master.getDates()){
            date.setMaster(master);
        }
        for(MasterReview review: master.getReviews()){
            review.setMaster(master);
        }


        return masterRepository.save(master);
    }

    private void updatedMasterInfo(Master masterToUpdate, Master updatedMaster) {
        masterToUpdate.setInfo(updatedMaster.getInfo());
        masterToUpdate.setDates(updatedMaster.getDates());
        masterToUpdate.setMaintenances(updatedMaster.getMaintenances());
        masterToUpdate.setReviews(updatedMaster.getReviews());
    }

    public Master findMasterById(Long id) {
        return masterRepository.findById(id)
                .orElseThrow(() -> new NoSuchMasterException(id));
    }

}
