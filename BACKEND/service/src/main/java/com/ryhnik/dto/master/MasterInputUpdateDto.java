package com.ryhnik.dto.master;

import javax.validation.constraints.NotBlank;

public class MasterInputUpdateDto {

    @NotBlank
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
