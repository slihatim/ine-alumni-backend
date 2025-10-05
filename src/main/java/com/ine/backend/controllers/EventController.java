package com.ine.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.ApiResponseDto;
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

	// Create event - only users with "events:create" authority
	@PostMapping
	@PreAuthorize("hasAuthority('events:create')")
	public ResponseEntity<ApiResponseDto<EventResponseDto>> createEvent(
			@Valid @RequestBody EventRequestDto eventRequest) {
		try {
			EventResponseDto newEvent = eventService.createEvent(eventRequest);
			ApiResponseDto<EventResponseDto> response = ApiResponseDto.<EventResponseDto>builder().isSuccess(true)
					.message("Event created successfully").response(newEvent).build();
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			ApiResponseDto<EventResponseDto> response = ApiResponseDto.<EventResponseDto>builder().isSuccess(false)
					.message("Error occurred while creating event: " + e.getMessage()).response(null).build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// Get all events - accessible to users with "events:read" authority
	@GetMapping("/public")
	// @PreAuthorize("hasAuthority('events:read')")

	public ResponseEntity<ApiResponseDto<List<EventResponseDto>>> getAllEvents() {
		try {
			List<EventResponseDto> events = eventService.getAllEvents();
			ApiResponseDto<List<EventResponseDto>> response = ApiResponseDto.<List<EventResponseDto>>builder()
					.isSuccess(true).message("Events retrieved successfully").response(events).build();
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponseDto<List<EventResponseDto>> response = ApiResponseDto.<List<EventResponseDto>>builder()
					.isSuccess(false).message("Error occurred while retrieving events: " + e.getMessage())
					.response(null).build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// Get event by ID - accessible to users with "events:read" authority
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('events:read')")
	public ResponseEntity<ApiResponseDto<EventResponseDto>> getEventById(@PathVariable Long id) {
		try {
			return eventService.getEventById(id).map(event -> {
				ApiResponseDto<EventResponseDto> response = ApiResponseDto.<EventResponseDto>builder().isSuccess(true)
						.message("Event found").response(event).build();
				return ResponseEntity.ok(response);
			}).orElseGet(() -> {
				ApiResponseDto<EventResponseDto> response = ApiResponseDto.<EventResponseDto>builder().isSuccess(false)
						.message("Event not found").response(null).build();
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			});
		} catch (Exception e) {
			ApiResponseDto<EventResponseDto> response = ApiResponseDto.<EventResponseDto>builder().isSuccess(false)
					.message("Error occurred while retrieving event: " + e.getMessage()).response(null).build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// Update event - only users with "events:update" authority
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('events:update')")
	public ResponseEntity<ApiResponseDto<EventResponseDto>> updateEvent(@PathVariable Long id,
			@Valid @RequestBody EventRequestDto eventRequest) {
		try {
			return eventService.updateEvent(id, eventRequest).map(updatedEvent -> {
				ApiResponseDto<EventResponseDto> response = ApiResponseDto.<EventResponseDto>builder().isSuccess(true)
						.message("Event updated successfully").response(updatedEvent).build();
				return ResponseEntity.ok(response);
			}).orElseGet(() -> {
				ApiResponseDto<EventResponseDto> response = ApiResponseDto.<EventResponseDto>builder().isSuccess(false)
						.message("Event not found").response(null).build();
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			});
		} catch (Exception e) {
			ApiResponseDto<EventResponseDto> response = ApiResponseDto.<EventResponseDto>builder().isSuccess(false)
					.message("Error occurred while updating event: " + e.getMessage()).response(null).build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// Delete event - only users with "events:delete" authority
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('events:delete')")
	public ResponseEntity<ApiResponseDto<Void>> deleteEvent(@PathVariable Long id) {
		try {
			boolean deleted = eventService.deleteEvent(id);
			if (deleted) {
				ApiResponseDto<Void> response = ApiResponseDto.<Void>builder().isSuccess(true)
						.message("Event deleted successfully").response(null).build();
				return ResponseEntity.ok(response);
			} else {
				ApiResponseDto<Void> response = ApiResponseDto.<Void>builder().isSuccess(false)
						.message("Event not found").response(null).build();
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			ApiResponseDto<Void> response = ApiResponseDto.<Void>builder().isSuccess(false)
					.message("Error occurred while deleting event: " + e.getMessage()).response(null).build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}