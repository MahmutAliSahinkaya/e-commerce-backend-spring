package com.ecommerce.userservice.service.impl;

import com.ecommerce.userservice.entity.Role;
import com.ecommerce.userservice.entity.enums.ERole;
import com.ecommerce.userservice.repository.RoleRepository;
import com.ecommerce.userservice.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
