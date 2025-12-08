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

	@Value("${spring.mail.username}")
	private String senderEmail;

	// Email de destination pour recevoir les messages de contact
	private static final String CONTACT_EMAIL = "inealumni.i@gmail.com";

	public void sendMessage(ContactMessage message) {
		// 1️⃣ Sauvegarde du message dans la base de données
		repository.save(message);

		// 2️⃣ Email envoyé à INE Alumni (notification du nouveau message)
		SimpleMailMessage mailToAdmin = new SimpleMailMessage();
		mailToAdmin.setTo(CONTACT_EMAIL); // ✅ Destination: INE Alumni
		mailToAdmin.setFrom(senderEmail); // ✅ Expéditeur: votre email configuré
		mailToAdmin.setReplyTo(message.getEmail()); // ✅ Répondre directement à l'utilisateur
		mailToAdmin.setSubject("📩 Nouveau message de contact : " + message.getObjet());
		mailToAdmin.setText("Vous avez reçu un nouveau message de contact :\n\n" + "Nom : " + message.getNom() + "\n"
				+ "Prénom : " + message.getPrenom() + "\n" + "Email : " + message.getEmail() + "\n" + "Objet : "
				+ message.getObjet() + "\n\n" + "Message :\n" + message.getMessage() + "\n\n" + "---\n"
				+ "Vous pouvez répondre directement en cliquant sur 'Répondre'.");

		// 3️⃣ Email de confirmation envoyé à l'utilisateur
		SimpleMailMessage mailToUser = new SimpleMailMessage();
		mailToUser.setTo(message.getEmail()); // ✅ Destination: l'utilisateur
		mailToUser.setFrom(senderEmail);
		mailToUser.setSubject("✅ Confirmation de réception - " + message.getObjet());
		mailToUser.setText("Bonjour " + message.getPrenom() + " " + message.getNom() + ",\n\n"
				+ "Nous avons bien reçu votre message concernant : " + message.getObjet() + "\n\n"
				+ "Notre équipe vous répondra dans les plus brefs délais.\n\n" + "Cordialement,\n"
				+ "L'équipe INE Alumni");

		// 4️⃣ Envoi des emails avec gestion d'erreurs
		try {
			mailSender.send(mailToAdmin); // Email à INE Alumni
			mailSender.send(mailToUser); // Email de confirmation à l'utilisateur
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors de l'envoi de l'email : " + e.getMessage());
		}
	}
}