package com.ine.backend.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ine.backend.entities.User;
import com.ine.backend.repositories.AdminRepository;
import com.ine.backend.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private UserRepository userRepository;
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			user = adminRepository.findByEmail(username);
			if (user == null) {
				throw new UsernameNotFoundException("User not found with email: " + username);
			}
		} /*
			 * else if(!user.getIsEmailVerified()){ throw new
			 * EmailVerificationException("l'email n'est pas vérifié."); }
			 */
		return UserDetailsImpl.build(user);
	}
}
