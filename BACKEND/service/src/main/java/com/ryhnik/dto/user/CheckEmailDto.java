package com.ryhnik.dto.user;

import javax.validation.constraints.NotBlank;

public class CheckEmailDto {

    @NotBlank
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
