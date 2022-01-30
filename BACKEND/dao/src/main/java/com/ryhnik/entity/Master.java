package com.ryhnik.entity;

import com.ryhnik.entity.core.BaseAuditableEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "masters")
public class Master extends BaseAuditableEntity {

    @Column(name = "started_at")
    private LocalDate startedAt;

    private String info;

    @Enumerated(EnumType.STRING)
    private MasterCategory category;

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

    public void setInfo(String info) {
        this.info = info;
    }
}
