package com.ryhnik.dto.user;

import com.ryhnik.entity.MasterCategory;

import java.time.LocalDateTime;

public class UserInputCreateDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private UserRoleDto role;
    private String info;
    private Long startedAt;
    private MasterCategory category;

    public MasterCategory getCategory() {
        return category;
    }

    public void setCategory(MasterCategory category) {
        this.category = category;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRoleDto getRole() {
        return role;
    }

    public void setRole(UserRoleDto role) {
        this.role = role;
    }
}
