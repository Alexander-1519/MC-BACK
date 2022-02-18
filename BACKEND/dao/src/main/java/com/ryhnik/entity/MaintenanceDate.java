package com.ryhnik.entity;

import com.ryhnik.entity.Master;
import com.ryhnik.entity.core.BaseAuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_date")
public class MaintenanceDate extends BaseAuditableEntity {

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    private Master master;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }
}
