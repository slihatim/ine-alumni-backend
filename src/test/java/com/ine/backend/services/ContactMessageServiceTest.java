package com.ine.backend.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import com.ine.backend.entities.ContactMessage;
import com.ine.backend.repositories.ContactMessageRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContactMessageService Unit Tests")
class ContactMessageServiceTest {

	@Mock
	private ContactMessageRepository repository;

	@Mock
	private JavaMailSender mailSender;

	@InjectMocks
	private ContactMessageService service;

	@BeforeEach
	void setUp() {
		// Set the @Value field using reflection
		ReflectionTestUtils.setField(service, "senderEmail", "test@ine.alumni.ma");
	}

	@Test
	@DisplayName("Should save message and send email successfully")
	void givenValidMessage_whenSendMessage_thenSaveAndSendEmail() {
		// Given
		ContactMessage message = new ContactMessage();
		message.setEmail("user@example.com");
		message.setPrenom("John");
		message.setNom("Doe");
		message.setObjet("Test Subject");
		message.setMessage("Test message content");

		when(repository.save(any(ContactMessage.class))).thenReturn(message);

		// When
		service.sendMessage(message);

		// Then
		verify(repository, times(1)).save(message);
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
	}

	@Test
	@DisplayName("Should throw RuntimeException when email sending fails")
	void givenEmailFailure_whenSendMessage_thenThrowException() {
		// Given
		ContactMessage message = new ContactMessage();
		message.setEmail("user@example.com");
		message.setPrenom("John");
		message.setNom("Doe");
		message.setObjet("Test");
		message.setMessage("Test");

		when(repository.save(any(ContactMessage.class))).thenReturn(message);
		org.mockito.Mockito.doThrow(new RuntimeException("SMTP server unavailable")).when(mailSender)
				.send(any(SimpleMailMessage.class));

		// When & Then
		assertThatThrownBy(() -> service.sendMessage(message)).isInstanceOf(RuntimeException.class)
				.hasMessage("Erreur lors de l'envoi de l'email");

		verify(repository, times(1)).save(message);
	}
}
