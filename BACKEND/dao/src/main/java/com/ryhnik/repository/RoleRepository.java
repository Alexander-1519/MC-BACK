package com.ryhnik.repository;

import com.ryhnik.entity.UserRole;
import com.ryhnik.entity.UserRoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByName(UserRoleName name);
}
