package com.ine.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.entities.ContactMessage;
import com.ine.backend.services.ContactMessageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class ContactMessageController {

	private final ContactMessageService contactMessageService;

	// 🔹 Endpoint utilisé par le front
	@PostMapping
	public ResponseEntity<String> sendMessage(@Valid @RequestBody ContactMessage message) {
		contactMessageService.sendMessage(message);
		return ResponseEntity.ok("Message envoyé avec succès !");
	}

}
