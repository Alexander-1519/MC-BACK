package com.ryhnik.dto.master;

import com.ryhnik.dto.maintenance.MaintenanceInputCreateDto;
import com.ryhnik.dto.maintenancedate.MaintenanceDateInputCreateDto;
import com.ryhnik.entity.PortfolioImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MasterFullInputCreateDto {

    private String info;
    private Long startedAt;
    private List<MaintenanceInputCreateDto> maintenances;
    private List<MaintenanceDateInputCreateDto> dates;
    private List<PortfolioImage> images;

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

    public List<MaintenanceInputCreateDto> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<MaintenanceInputCreateDto> maintenances) {
        this.maintenances = maintenances;
    }

    public List<MaintenanceDateInputCreateDto> getDates() {
        return dates;
    }

    public void setDates(List<MaintenanceDateInputCreateDto> dates) {
        this.dates = dates;
    }

    public List<PortfolioImage> getImages() {
        return images;
    }

    public void setImages(List<PortfolioImage> images) {
        this.images = images;
    }
}
