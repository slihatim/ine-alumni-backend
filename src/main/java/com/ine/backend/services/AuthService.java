package com.ine.backend.services;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ine.backend.dto.SignInRequestDto;
import com.ine.backend.dto.SignInResponseDto;
import com.ine.backend.dto.SignUpRequestDto;
import com.ine.backend.entities.*;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.repositories.AdminRepository;
import com.ine.backend.security.jwt.JwtUtils;
import com.ine.backend.services.impl.UserDetailsImpl;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	private UserService userService;
	private AdminRepository adminRepository;
	private PasswordEncoder passwordEncoder;

	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;

	public void signUpUser(SignUpRequestDto requestDto) throws UserAlreadyExistsException {
		// Check email uniqueness across ALL user types
		if (emailExistsInSystem(requestDto.getEmail())) {
			throw new UserAlreadyExistsException(
					"Registration failed: The provided email already exists. Please sign in or use a different email.");
		}

		InptUser user = createUser(requestDto);
		userService.saveUser(user);
	}

	/**
	 * Check if email exists in any user repository (admins or regular users) This
	 * prevents email collisions across different user types
	 */
	public boolean emailExistsInSystem(String email) {
		return userService.existsByEmail(email) || adminRepository.existsByEmail(email);
	}

	/**
	 * Find user by email and role - polymorphic approach Looks up user in the
	 * appropriate repository based on their role
	 */
	private User findUserByEmailAndRole(String email, Role role) {
		if (role == Role.ROLE_ADMIN || role == Role.ROLE_SUPER_ADMIN) {
			return adminRepository.findByEmail(email);
		} else {
			return userService.findByEmail(email);
		}
	}

	private InptUser createUser(SignUpRequestDto requestDto) {
		InptUser user;

		LocalDate graduationDate = LocalDate.of(requestDto.getGraduationYear(), Month.JUNE, 1);
		LocalDate currentDate = LocalDate.now();

		if (currentDate.compareTo(graduationDate) >= 0) {
			user = new Laureat();
		} else {
			user = new Ine();
		}

		user.setFullName(requestDto.getFullName());
		user.setEmail(requestDto.getEmail());
		user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		user.setMajor(requestDto.getMajor());
		user.setGraduationYear(requestDto.getGraduationYear());
		user.setPhoneNumber(requestDto.getPhoneNumber());
		user.setBirthDate(requestDto.getBirthDate());
		user.setGender(requestDto.getGender());
		user.setCountry(requestDto.getCountry());
		user.setCity(requestDto.getCity());

		return user;
	}

	// for inpt_users and admins
	public SignInResponseDto signInUser(SignInRequestDto requestDto, boolean isAdmin) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.filter(authority -> authority.startsWith("ROLE_")).findFirst().orElse(null);

		Role userRole = Role.valueOf(role);

		// Validate that the user is using the correct signin endpoint
		validateSignInEndpoint(userRole, isAdmin);

		// Use polymorphic approach to get the user based on their role
		User user = findUserByEmailAndRole(userDetails.getUsername(), userRole);

		SignInResponseDto signInResponseDto = SignInResponseDto.builder().fullName(user.getFullName())
				.email(userDetails.getUsername()).token(jwt).type("Bearer").role(userRole)
				.isEmailVerified(user.getIsEmailVerified()).isAccountVerified(user.getIsAccountVerified()).build();

		return signInResponseDto;
	}

	/**
	 * Validate that users sign in through the correct endpoint Admins must use
	 * /admin/signin, regular users must use /signin
	 */
	private void validateSignInEndpoint(Role role, boolean isAdminEndpoint) {
		boolean isAdminRole = (role == Role.ROLE_ADMIN || role == Role.ROLE_SUPER_ADMIN);

		if (isAdminRole && !isAdminEndpoint) {
			throw new IllegalArgumentException("Admins must use the admin signin endpoint.");
		}

		if (!isAdminRole && isAdminEndpoint) {
			throw new IllegalArgumentException("This endpoint is reserved for administrators only.");
		}
	}

	public SignInResponseDto getAuthenticationState(String username, boolean isAdmin) {
		User user;
		if (isAdmin) {
			user = adminRepository.findByEmail(username);
		} else {
			user = userService.findByEmail(username);
		}

		SignInResponseDto signInResponseDto = SignInResponseDto.builder().fullName(user.getFullName()).email(username)
				.token(null).type("Bearer").role(user.getRole()).isEmailVerified(user.getIsEmailVerified())
				.isAccountVerified(user.getIsAccountVerified()).build();

		return signInResponseDto;
	}
}
