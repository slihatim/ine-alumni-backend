package com.ine.backend.services;

import java.util.List;

import com.ine.backend.entities.InptUser;

public interface UserService {
	public InptUser getUser(Long id);

	public List<InptUser> getAllUsers();

	public InptUser saveUser(InptUser user);

	public Long deleteUser(Long id);

	boolean existsByEmail(String email);

	public InptUser findByEmail(String email);

	public boolean isEmailVerified(String email);
}
