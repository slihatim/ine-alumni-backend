package com.ine.backend.mappers;

import org.springframework.stereotype.Component;

import com.ine.backend.dto.ProfileResponseDto;
import com.ine.backend.entities.InptUser;

@Component
public class ProfileMapper {

	public ProfileResponseDto toProfileResponseDto(InptUser user) {
		return ProfileResponseDto.builder().id(user.getId()).email(user.getEmail()).fullName(user.getFullName())
				.major(user.getMajor()).graduationYear(user.getGraduationYear()).phoneNumber(user.getPhoneNumber())
				.birthDate(user.getBirthDate()).gender(user.getGender()).country(user.getCountry()).city(user.getCity())
				.role(user.getRole()).isAccountVerified(user.getIsAccountVerified())
				.isEmailVerified(user.getIsEmailVerified()).isOauthAccount(user.getIsOauthAccount())
				.linkedinId(user.getLinkedinId()).createdAt(user.getCreatedAt()).updatedAt(user.getUpdatedAt())
				.userType(user.getUserType()).build();
	}
}
