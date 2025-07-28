package com.ine.backend.services;

import com.ine.backend.entities.InptUser;

import java.util.List;

public interface UserService {
    public InptUser getUser(Long id);
    public List<InptUser> getAllUsers();
    public InptUser saveUser(InptUser user);
    public Long deleteUser(Long id);

    boolean existsByEmail(String email);
}
