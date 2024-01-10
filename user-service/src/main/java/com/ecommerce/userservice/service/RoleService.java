package com.ecommerce.userservice.service;

import com.ecommerce.userservice.entity.Role;
import com.ecommerce.userservice.entity.enums.ERole;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERole name);

    void saveRole(Role role);

}
