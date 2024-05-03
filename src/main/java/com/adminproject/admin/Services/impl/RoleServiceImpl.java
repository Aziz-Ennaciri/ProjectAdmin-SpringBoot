package com.adminproject.admin.Services.impl;

import com.adminproject.admin.DAO.RoleRepository;
import com.adminproject.admin.Entities.Role;
import com.adminproject.admin.Services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role createRole(String roleName) {
        return roleRepository.save(new Role(roleName));
    }
}
