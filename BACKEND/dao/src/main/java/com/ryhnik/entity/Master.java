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
}