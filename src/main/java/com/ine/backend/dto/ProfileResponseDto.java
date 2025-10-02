package com.ine.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ine.backend.entities.Gender;
import com.ine.backend.entities.Major;
import com.ine.backend.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {
	private Long id;
	private String email;
	private String fullName;
	private Major major;
	private Integer graduationYear;
	private String phoneNumber;
	private LocalDate birthDate;
	private Gender gender;
	private String country;
	private String city;
	private Role role;
	private Boolean isAccountVerified;
	private Boolean isEmailVerified;
	private Boolean isOauthAccount;
	private String linkedinId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String userType;
}
