package com.ine.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TestController.class)
@ActiveProfiles("test")
@DisplayName("TestController Unit Tests")
class TestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Should return 401 when accessing endpoint without authentication")
	void givenNoAuthentication_whenReadAnyEvent_thenReturnUnauthorized() throws Exception {
		// When & Then
		mockMvc.perform(get("/api/v1/test/events/read")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(authorities = "events:read")
	@DisplayName("Should return success message when user has correct authority")
	void givenUserWithReadAuthority_whenReadAnyEvent_thenReturnSuccess() throws Exception {
		// When & Then
		mockMvc.perform(get("/api/v1/test/events/read")).andExpect(status().isOk())
				.andExpect(content().string("read any event"));
	}

	@Test
	@WithMockUser(authorities = "events:update:self")
	@DisplayName("Should return success when accessing update-own-event with correct authority")
	void givenUserWithUpdateSelfAuthority_whenUpdateOwnEvent_thenReturnSuccess() throws Exception {
		// When & Then
		mockMvc.perform(get("/api/v1/test/events/update-own-event")).andExpect(status().isOk())
				.andExpect(content().string("update own event"));
	}
}
