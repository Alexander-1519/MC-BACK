package com.ryhnik.entity;

import com.ryhnik.entity.core.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class UserRole extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoleName name;
}
