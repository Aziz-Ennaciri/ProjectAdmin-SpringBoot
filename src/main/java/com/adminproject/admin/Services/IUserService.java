package com.adminproject.admin.Services;

import com.adminproject.admin.Entities.User;

public interface IUserService {
    User loadUserByEmail(String email);
    User createUser(String email,String password);
    void assignRoleToUser(String email,String roleName);
}
