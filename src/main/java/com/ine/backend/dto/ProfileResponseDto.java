package com.ine.backend.dto;

import java.time.LocalDate;

import com.ine.backend.entities.Gender;
import com.ine.backend.entities.Major;

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
	private String linkedinId;
}
