package com.project.repository.user;

import com.project.entity.concretes.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer > {
}
