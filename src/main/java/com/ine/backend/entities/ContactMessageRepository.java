package com.ine.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ine.backend.entities.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
