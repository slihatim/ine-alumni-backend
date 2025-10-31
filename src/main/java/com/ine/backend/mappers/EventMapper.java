package com.ine.backend.mappers;

import org.springframework.stereotype.Component;

import com.ine.backend.dto.EventRequestDto;
import com.ine.backend.dto.EventResponseDto;
import com.ine.backend.entities.Event;

@Component
public class EventMapper {

	public Event toEntity(EventRequestDto requestDto) {
		return Event.builder().title(requestDto.getTitle()).description(requestDto.getDescription())
				.date(requestDto.getDate()).location(requestDto.getLocation()).club(requestDto.getClub())
				.image(requestDto.getImage()).schedule(requestDto.getSchedule())
				.expectations(requestDto.getExpectations()).build();
	}

	public EventResponseDto toResponseDto(Event event) {
		return EventResponseDto.builder().id(event.getId()).title(event.getTitle()).description(event.getDescription())
				.date(event.getDate()).location(event.getLocation()).club(event.getClub()).image(event.getImage())
				.schedule(event.getSchedule()).expectations(event.getExpectations()).build();
	}

	public void updateEntityFromRequest(EventRequestDto requestDto, Event event) {
		event.setTitle(requestDto.getTitle());
		event.setDescription(requestDto.getDescription());
		event.setDate(requestDto.getDate());
		event.setLocation(requestDto.getLocation());
		event.setClub(requestDto.getClub());
		event.setImage(requestDto.getImage());
		event.setSchedule(requestDto.getSchedule());
		event.setExpectations(requestDto.getExpectations());
	}
}
