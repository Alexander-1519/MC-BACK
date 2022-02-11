package com.ryhnik.dto.master;

import java.time.LocalDate;

public class MasterOutputDto {

    private String info;
    private LocalDate startedAt;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }
}
