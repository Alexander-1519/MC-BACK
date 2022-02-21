package com.ryhnik.entity;

import com.ryhnik.entity.core.BaseAuditableEntity;

import javax.persistence.*;

@Entity
@Table(name = "portfolio_images")
public class PortfolioImage extends BaseAuditableEntity {

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne
    private Master master;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }
}
