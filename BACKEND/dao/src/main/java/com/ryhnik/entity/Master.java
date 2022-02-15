package com.ryhnik.entity;

import com.ryhnik.entity.core.BaseAuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "masters")
public class Master extends BaseAuditableEntity {

    @Column(name = "started_at")
    private LocalDate startedAt;

    private String info;

    @Enumerated(EnumType.STRING)
    private MasterCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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
}