package com.ine.backend.services;

import com.ine.backend.entities.User;

import java.util.List;

public interface UserService {
    public User getUser(Long id);
    public List<User> getAllUsers();
    public User saveUser(User user);
    public Long deleteUser(Long id);

    boolean existsByEmail(String email);
}
