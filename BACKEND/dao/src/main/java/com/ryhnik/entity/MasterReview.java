package com.ryhnik.entity;

import com.ryhnik.entity.core.BaseAuditableEntity;

import javax.persistence.*;

@Entity
@Table(name = "master_reviews")
public class MasterReview extends BaseAuditableEntity {

    @Column(nullable = false)
    private String review;

    private Double rating;

    @ManyToOne
    private Master master;

    @ManyToOne
    private User user;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
