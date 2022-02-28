package com.ryhnik.dto.master;

import com.ryhnik.dto.maintenance.MaintenanceOutputDto;
import com.ryhnik.dto.maintenancedate.MaintenanceDateOutputDto;
import com.ryhnik.dto.masterreview.MasterReviewOutputDto;
import com.ryhnik.dto.portfolioimage.PortfolioImageOutputDto;

import java.time.LocalDate;
import java.util.List;

public class MasterFullOutputDto {

    private String info;
    private Long startedAt;
    private List<MasterReviewOutputDto> reviews;
    private List<PortfolioImageOutputDto> images;
    private List<MaintenanceDateOutputDto> dates;
    private List<MaintenanceOutputDto> maintenances;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }

    public List<MasterReviewOutputDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<MasterReviewOutputDto> reviews) {
        this.reviews = reviews;
    }

    public List<PortfolioImageOutputDto> getImages() {
        return images;
    }

    public void setImages(List<PortfolioImageOutputDto> images) {
        this.images = images;
    }

    public List<MaintenanceDateOutputDto> getDates() {
        return dates;
    }

    public void setDates(List<MaintenanceDateOutputDto> dates) {
        this.dates = dates;
    }

    public List<MaintenanceOutputDto> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<MaintenanceOutputDto> maintenances) {
        this.maintenances = maintenances;
    }
}
