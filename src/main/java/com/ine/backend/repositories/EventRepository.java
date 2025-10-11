package com.ine.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ine.backend.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> { // CHANGED: interface name from
																		// EvenementRepository to EventRepository
																		// (English naming)
	// Custom methods can be added here later if needed // CHANGED: comment from
	// French to English
}
