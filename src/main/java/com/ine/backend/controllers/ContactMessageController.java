package com.ine.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.entities.ContactMessage;
import com.ine.backend.services.ContactMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ContactMessageController {

	private final ContactMessageService contactMessageService;

	// 🔹 Endpoint utilisé par le front
	@PostMapping
	public ResponseEntity<String> sendMessage(@RequestBody ContactMessage message) {
		contactMessageService.sendMessage(message);
		return ResponseEntity.ok("Message envoyé avec succès !");
	}

	// 🔹 Endpoint de test pour vérifier que l'envoi d'email fonctionne (sans front)
	@GetMapping("/test")
	public ResponseEntity<String> testSendEmail() {
		ContactMessage testMessage = new ContactMessage();
		testMessage.setNom("Testeur");
		testMessage.setPrenom("Backend");
		testMessage.setEmail("tonemail@gmail.com"); // 🔁 remplace par ton adresse Gmail
		testMessage.setObjet("Test d’envoi depuis Spring Boot");
		testMessage.setMessage("Ceci est un email de test envoyé depuis le backend 🚀");

		contactMessageService.sendMessage(testMessage);
		return ResponseEntity.ok("✅ Email de test envoyé avec succès !");
	}
}
