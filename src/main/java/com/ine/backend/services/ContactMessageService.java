package com.ine.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ine.backend.entities.ContactMessage;
import com.ine.backend.repositories.ContactMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

	private final ContactMessageRepository repository;
	private final JavaMailSender mailSender;

	// ✅ Inject sender email from environment variable
	@Value("${spring.mail.username}")
	private String senderEmail;

	public void sendMessage(ContactMessage message) {
		// 1️⃣ Sauvegarde du message dans la base de données
		repository.save(message);

		// 2️⃣ Préparation de l’e-mail
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(message.getEmail()); // ✅ destinataire = email saisi par l’utilisateur
		mail.setFrom(senderEmail); // ✅ expéditeur depuis variable d’environnement
		mail.setSubject("📩 Merci pour votre message : " + message.getObjet());
		mail.setText("Bonjour " + message.getPrenom() + " " + message.getNom() + ",\n\n"
				+ "Nous avons bien reçu votre message :\n\n" + message.getMessage() + "\n\n"
				+ "Nous vous contacterons bientôt.\n\n" + "Cordialement,\nL’équipe INE Alumni");

		// 3️⃣ Envoi et gestion des erreurs
		try {
			mailSender.send(mail);
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors de l'envoi de l'email");
		}
	}
}
