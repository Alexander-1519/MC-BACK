package com.ryhnik.dto.master.filter;

import com.ryhnik.entity.MasterCategory;

public class MasterFilterDto {

    private MasterCategory category;
    private Long experience;

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public MasterCategory getCategory() {
        return category;
    }

    public void setCategory(MasterCategory category) {
        this.category = category;
    }
}
