package com.ine.backend.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ine.backend.entities.InptUser;
import com.ine.backend.repositories.UserRepository;
import com.ine.backend.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Override
	public InptUser getUser(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public List<InptUser> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public InptUser saveUser(InptUser user) {
		return userRepository.save(user);
	}

	@Override
	public Long deleteUser(Long id) {
		userRepository.deleteById(id);
		return id;
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public InptUser findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean isEmailVerified(String email) {
		return userRepository.existsByEmailAndIsEmailVerified(email, true);
	}
}
