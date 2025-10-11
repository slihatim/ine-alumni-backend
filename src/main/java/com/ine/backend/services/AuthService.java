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
import com.ine.backend.security.jwt.JwtUtils;
import com.ine.backend.services.impl.UserDetailsImpl;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	private UserService userService;
	private PasswordEncoder passwordEncoder;

	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;

	public void signUpUser(SignUpRequestDto requestDto) throws UserAlreadyExistsException {
		if (userService.existsByEmail(requestDto.getEmail())) {
			throw new UserAlreadyExistsException(
					"Échec d'inscription : l'email fourni existe déjà. Essayez de vous connecter ou utilisez un autre email.");
		}

		InptUser user = createUser(requestDto);
		userService.saveUser(user);
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

	public SignInResponseDto signInUser(SignInRequestDto requestDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.filter(authority -> authority.startsWith("ROLE_")).findFirst().orElse(null);

		InptUser user = userService.findByEmail(userDetails.getUsername());

		SignInResponseDto signInResponseDto = SignInResponseDto.builder().fullName(user.getFullName())
				.email(userDetails.getUsername()).token(jwt).type("Bearer").role(Role.valueOf(role))
				.isEmailVerified(user.getIsEmailVerified()).isAccountVerified(user.getIsAccountVerified()).build();

		return signInResponseDto;
	}

	public SignInResponseDto getAuthenticationState(String username) {
		InptUser user = userService.findByEmail(username);

		SignInResponseDto signInResponseDto = SignInResponseDto.builder().fullName(user.getFullName()).email(username)
				.token(null).type("Bearer").role(user.getRole()).isEmailVerified(user.getIsEmailVerified())
				.isAccountVerified(user.getIsAccountVerified()).build();

		return signInResponseDto;
	}
}
