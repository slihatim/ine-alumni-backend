package com.ine.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.EventRequestDto;
import com.ine.backend.dto.EventResponseDto;
import com.ine.backend.services.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping
	// TODO: reactivate when implementing roles
	// @PreAuthorize("hasAuthority('events:create')")
	public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto eventRequest) {
		EventResponseDto newEvent = eventService.createEvent(eventRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
	}

	@GetMapping("/public")
	public ResponseEntity<List<EventResponseDto>> getAllEvents() {
		List<EventResponseDto> events = eventService.getAllEvents();
		return ResponseEntity.ok(events);
	}

	@GetMapping("/public/{id}")
	public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
		return eventService.getEventById(id).map(event -> ResponseEntity.ok(event))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PutMapping("/{id}")
	// TODO: reactivate when implementing roles
	// @PreAuthorize("hasAuthority('events:update')")
	public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Long id,
			@Valid @RequestBody EventRequestDto eventRequest) {
		Optional<EventResponseDto> updatedEventOpt = eventService.updateEvent(id, eventRequest);
		if (updatedEventOpt.isPresent()) {
			return ResponseEntity.ok(updatedEventOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/{id}")
  // TODO: reactivate when implementing roles
	// @PreAuthorize("hasAuthority('events:delete')")
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
		boolean deleted = eventService.deleteEvent(id);
		if (deleted) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}