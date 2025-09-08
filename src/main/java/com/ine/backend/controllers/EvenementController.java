package com.ine.backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.entities.Evenement;
import com.ine.backend.services.EvenementService;

@RestController
@RequestMapping("/api/evenements")
@CrossOrigin(origins = "*")
public class EvenementController {

	private final EvenementService evenementService;

	public EvenementController(EvenementService evenementService) {
		this.evenementService = evenementService;
	}

	// ➕ Ajouter un événement → seulement les utilisateurs avec authority
	// "events:create"
	@PostMapping
	@PreAuthorize("hasAuthority('events:create')")
	public ResponseEntity<Evenement> ajouterEvenement(@RequestBody Evenement evenement) {
		Evenement nouveau = evenementService.ajouterEvenement(evenement);
		return ResponseEntity.ok(nouveau);
	}

	// 📋 Récupérer tous les événements → accessible à tous avec authority
	// "events:read"
	@GetMapping
	@PreAuthorize("hasAuthority('events:read')")
	public ResponseEntity<List<Evenement>> getAll() {
		return ResponseEntity.ok(evenementService.getTousLesEvenements());
	}

	// 🔎 Récupérer un événement par ID → accessible à tous avec authority
	// "events:read"
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('events:read')")
	public ResponseEntity<Evenement> getById(@PathVariable Long id) {
		return evenementService.getEvenementParId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// ❌ Supprimer un événement → seulement les utilisateurs avec authority
	// "events:delete"
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('events:delete')")
	public ResponseEntity<Void> supprimer(@PathVariable Long id) {
		evenementService.supprimerEvenement(id);
		return ResponseEntity.noContent().build();
	}
}
