package com.ine.backend.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ine.backend.dto.EventRequestDto;
import com.ine.backend.dto.EventResponseDto;
import com.ine.backend.entities.Event;
import com.ine.backend.mappers.EventMapper;
import com.ine.backend.repositories.EventRepository;
import com.ine.backend.services.EventService;

@Service
@Transactional
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;
	private final EventMapper eventMapper;

	public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
		this.eventRepository = eventRepository;
		this.eventMapper = eventMapper;
	}

	@Override
	public EventResponseDto createEvent(EventRequestDto eventRequest) {
		Event event = eventMapper.toEntity(eventRequest);
		Event savedEvent = eventRepository.save(event);
		return eventMapper.toResponseDto(savedEvent);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EventResponseDto> getAllEvents() {
		List<Event> events = eventRepository.findAll();
		return events.stream().map(eventMapper::toResponseDto).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<EventResponseDto> getEventById(Long id) {
		return eventRepository.findById(id).map(eventMapper::toResponseDto);
	}

	@Override
	public Optional<EventResponseDto> updateEvent(Long id, EventRequestDto eventRequest) {
		return eventRepository.findById(id).map(existingEvent -> {
			eventMapper.updateEntityFromRequest(eventRequest, existingEvent);
			Event updatedEvent = eventRepository.save(existingEvent);
			return eventMapper.toResponseDto(updatedEvent);
		});
	}

	@Override
	public boolean deleteEvent(Long id) {
		if (eventRepository.existsById(id)) {
			eventRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
