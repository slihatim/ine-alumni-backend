package com.ine.backend.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ine.backend.dto.ChangeEmailRequestDto;
import com.ine.backend.dto.ChangePasswordRequestDto;
import com.ine.backend.dto.ProfileResponseDto;
import com.ine.backend.dto.ProfileUpdateRequestDto;
import com.ine.backend.entities.Gender;
import com.ine.backend.entities.Major;
import com.ine.backend.entities.Role;
import com.ine.backend.services.ProfileService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProfileService profileService;

	@Autowired
	private ObjectMapper objectMapper;

	private ProfileResponseDto sampleProfile;
	private ProfileUpdateRequestDto updateRequest;

	@BeforeEach
	void setUp() {
		sampleProfile = ProfileResponseDto.builder().id(1L).email("test@ine.ac.ma").fullName("Test User")
				.major(Major.GI).graduationYear(2023).phoneNumber("+212123456789").birthDate(LocalDate.of(1995, 1, 1))
				.gender(Gender.MALE).country("Morocco").city("Rabat").role(Role.ROLE_USER).isAccountVerified(true)
				.isEmailVerified(true).isOauthAccount(false).createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now()).userType("INE").build();

		updateRequest = ProfileUpdateRequestDto.builder().fullName("Updated User").major(Major.GI).graduationYear(2023)
				.phoneNumber("+212987654321").birthDate(LocalDate.of(1995, 1, 1)).gender(Gender.MALE).country("Morocco")
				.city("Casablanca").build();
	}

	@Test
    @WithMockUser(username = "test@ine.ac.ma")
    void getCurrentUserProfile_ShouldReturnProfile() throws Exception {
        when(profileService.getCurrentUserProfile("test@ine.ac.ma")).thenReturn(sampleProfile);

        mockMvc.perform(get("/api/v1/profile/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Profil récupéré avec succès"))
                .andExpect(jsonPath("$.data.email").value("test@ine.ac.ma"))
                .andExpect(jsonPath("$.data.fullName").value("Test User"));

        verify(profileService).getCurrentUserProfile("test@ine.ac.ma");
    }

	@Test
    @WithMockUser(username = "admin@ine.ac.ma", roles = {"ADMIN"})
    void getUserProfile_AsAdmin_ShouldReturnProfile() throws Exception {
        when(profileService.getUserProfile(1L, "admin@ine.ac.ma")).thenReturn(sampleProfile);

        mockMvc.perform(get("/api/v1/profile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1));

        verify(profileService).getUserProfile(1L, "admin@ine.ac.ma");
    }

	@Test
	@WithMockUser(username = "test@ine.ac.ma")
	void updateCurrentUserProfile_ShouldReturnUpdatedProfile() throws Exception {
		ProfileResponseDto updatedProfile = ProfileResponseDto.builder().id(1L).email("test@ine.ac.ma")
				.fullName("Updated User").major(Major.GI).graduationYear(2023).phoneNumber("+212987654321")
				.birthDate(LocalDate.of(1995, 1, 1)).gender(Gender.MALE).country("Morocco").city("Casablanca")
				.role(Role.ROLE_USER).isAccountVerified(true).isEmailVerified(true).isOauthAccount(false)
				.createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).userType("INE").build();

		when(profileService.updateCurrentUserProfile(eq("test@ine.ac.ma"), any(ProfileUpdateRequestDto.class)))
				.thenReturn(updatedProfile);

		mockMvc.perform(put("/api/v1/profile/me").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.fullName").value("Updated User"))
				.andExpect(jsonPath("$.data.city").value("Casablanca"));

		verify(profileService).updateCurrentUserProfile(eq("test@ine.ac.ma"), any(ProfileUpdateRequestDto.class));
	}

	@Test
	@WithMockUser(username = "admin@ine.ac.ma", roles = {"ADMIN"})
	void updateUserProfile_AsAdmin_ShouldReturnUpdatedProfile() throws Exception {
		ProfileResponseDto updatedProfile = ProfileResponseDto.builder().id(1L).email("test@ine.ac.ma")
				.fullName("Updated User").build();

		when(profileService.updateUserProfile(eq(1L), eq("admin@ine.ac.ma"), any(ProfileUpdateRequestDto.class)))
				.thenReturn(updatedProfile);

		mockMvc.perform(put("/api/v1/profile/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));

		verify(profileService).updateUserProfile(eq(1L), eq("admin@ine.ac.ma"), any(ProfileUpdateRequestDto.class));
	}

	@Test
	@WithMockUser(username = "test@ine.ac.ma")
	void changeUserEmail_ShouldReturnSuccessMessage() throws Exception {
		ChangeEmailRequestDto changeEmailRequest = ChangeEmailRequestDto.builder().newEmail("newemail@ine.ac.ma")
				.build();

		when(profileService.changeUserEmail(eq("test@ine.ac.ma"), any(ChangeEmailRequestDto.class)))
				.thenReturn("L'adresse email a été modifiée avec succès.");

		mockMvc.perform(put("/api/v1/profile/change-email").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(changeEmailRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("L'adresse email a été modifiée avec succès."));

		verify(profileService).changeUserEmail(eq("test@ine.ac.ma"), any(ChangeEmailRequestDto.class));
	}

	@Test
	@WithMockUser(username = "test@ine.ac.ma")
	void changeUserPassword_ShouldReturnSuccessMessage() throws Exception {
		ChangePasswordRequestDto changePasswordRequest = ChangePasswordRequestDto.builder()
				.currentPassword("oldPassword123").newPassword("newPassword123").confirmPassword("newPassword123")
				.build();

		when(profileService.changeUserPassword(eq("test@ine.ac.ma"), any(ChangePasswordRequestDto.class)))
				.thenReturn("Le mot de passe a été modifié avec succès.");

		mockMvc.perform(put("/api/v1/profile/change-password").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(changePasswordRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("Le mot de passe a été modifié avec succès."));

		verify(profileService).changeUserPassword(eq("test@ine.ac.ma"), any(ChangePasswordRequestDto.class));
	}

	@Test
    @WithMockUser(username = "test@ine.ac.ma")
    void deactivateAccount_ShouldReturnSuccessMessage() throws Exception {
        when(profileService.deactivateAccount("test@ine.ac.ma"))
                .thenReturn("Le compte a été désactivé avec succès.");

        mockMvc.perform(put("/api/v1/profile/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Le compte a été désactivé avec succès."));

        verify(profileService).deactivateAccount("test@ine.ac.ma");
    }

	@Test
    @WithMockUser(username = "superadmin@ine.ac.ma", roles = {"SUPER_ADMIN"})
    void deleteUserAccount_AsSuperAdmin_ShouldReturnSuccessMessage() throws Exception {
        when(profileService.deleteUserAccount(1L, "superadmin@ine.ac.ma"))
                .thenReturn("Le compte utilisateur a été supprimé avec succès.");

        mockMvc.perform(delete("/api/v1/profile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Le compte utilisateur a été supprimé avec succès."));

        verify(profileService).deleteUserAccount(1L, "superadmin@ine.ac.ma");
    }

	@Test
	@WithMockUser(username = "user@ine.ac.ma")
	void deleteUserAccount_AsRegularUser_ShouldReturnForbidden() throws Exception {
		mockMvc.perform(delete("/api/v1/profile/1")).andExpect(status().isForbidden());

		verifyNoInteractions(profileService);
	}
}
