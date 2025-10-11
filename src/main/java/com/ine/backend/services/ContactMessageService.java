package com.ine.backend.services;

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

	public void sendMessage(ContactMessage message) {
		// 1️⃣ Sauvegarde du message dans la base de données
		repository.save(message);
		System.out.println("💾 Message sauvegardé dans la base : " + message);

		// 2️⃣ Préparation de l’e-mail
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("naimaelmaalmi12@gmail.com"); // ✅ ton adresse Gmail
		mail.setFrom("naimaelmaalmi12@gmail.com"); // ✅ expéditeur (doit être la même que celle configurée)
		mail.setSubject("📩 Nouveau message de contact : " + message.getObjet());
		mail.setText("Nom : " + message.getNom() + "\n" + "Prénom : " + message.getPrenom() + "\n" + "Email : "
				+ message.getEmail() + "\n\n" + "Message : \n" + message.getMessage());

		// 3️⃣ Envoi et gestion des erreurs
		try {
			System.out.println("🚀 Tentative d’envoi de l’e-mail...");
			mailSender.send(mail);
			System.out.println("✅ Email envoyé avec succès à " + mail.getTo()[0]);
		} catch (Exception e) {
			System.err.println("❌ Erreur lors de l’envoi de l’email : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
