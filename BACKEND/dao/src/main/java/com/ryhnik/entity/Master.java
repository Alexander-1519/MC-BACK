package com.ryhnik.entity;

import com.ryhnik.entity.core.BaseAuditableEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "masters")
public class Master extends BaseAuditableEntity {

    @Column(name = "started_at")
    private LocalDate startedAt;

    private String info;

    @Enumerated(EnumType.STRING)
    private MasterCategory category;

    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany
    private List<MaintenanceDate> dates;

    @OneToMany
    private List<Maintenance> maintenances;

    @OneToMany
    private List<PortfolioImage> images;

    @OneToMany
    private List<MasterReview> reviews;

    public MasterCategory getCategory() {
        return category;
    }

    public void setCategory(MasterCategory category) {
        this.category = category;
    }

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    public String getInfo() {
        return info;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<MaintenanceDate> getDates() {
        return dates;
    }

    public void setDates(List<MaintenanceDate> dates) {
        this.dates = dates;
    }

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }

    public List<PortfolioImage> getImages() {
        return images;
    }

    public void setImages(List<PortfolioImage> images) {
        this.images = images;
    }

    public List<MasterReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<MasterReview> reviews) {
        this.reviews = reviews;
    }
}