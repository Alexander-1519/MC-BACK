package com.ryhnik.dto.masterreview;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MasterReviewInputCreateDto {

    @NotBlank
    private String review;
    @Size(max = 5)
    private Double rating;

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
}
