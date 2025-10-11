package com.ine.backend.services;

import java.util.List;
import java.util.Optional;

import com.ine.backend.dto.EventRequestDto;
import com.ine.backend.dto.EventResponseDto;

public interface EventService {

	EventResponseDto createEvent(EventRequestDto eventRequest);

	List<EventResponseDto> getAllEvents();

	Optional<EventResponseDto> getEventById(Long id);

	Optional<EventResponseDto> updateEvent(Long id, EventRequestDto eventRequest);

	boolean deleteEvent(Long id);
}
