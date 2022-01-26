package com.ryhnik.dao.user;

import lombok.Data;

@Data
public class UserOutputDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String role;
}
