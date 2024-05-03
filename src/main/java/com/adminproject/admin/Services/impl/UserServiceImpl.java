package com.adminproject.admin.Services.impl;

import com.adminproject.admin.DAO.RoleRepository;
import com.adminproject.admin.DAO.UserRepository;
import com.adminproject.admin.Entities.Role;
import com.adminproject.admin.Entities.User;
import com.adminproject.admin.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        return userRepository.save(new User(email,password));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user= loadUserByEmail(email);
        Role role=roleRepository.findByName(roleName);
        user.assignRoleToUser(role);
    }
}
