package com.ine.backend.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

	@GetMapping("/events/read")
	@PreAuthorize("hasAuthority('events:read')")
	public String readAnyEvent() {
		return "read any event";
	}

	@GetMapping("/events/update-own-event")
	@PreAuthorize("hasAuthority('events:update:self')")
	public String updateOwnEvent() {
		return "update own event";
	}

	@GetMapping("/events/delete-own-event")
	@PreAuthorize("hasAuthority('events:delete:self')")
	public String deleteOwnEvent() {
		return "delete own event";
	}
}
