package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.entity.Role;
import com.ecommerce.userservice.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
