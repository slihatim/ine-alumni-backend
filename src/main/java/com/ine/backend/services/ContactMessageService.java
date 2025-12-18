package com.ine.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ine.backend.dto.ContactMessageRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${app.contact.to}")
    private String contactEmail;

    public void sendMessage(ContactMessageRequestDto request) {
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info(" Début d'envoi du message de contact");
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info(" Expéditeur: {} {}", request.getPrenom(), request.getNom());
        log.info(" Email utilisateur: {}", request.getEmail());
        log.info(" Objet: {}", request.getObjet());
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("  Configuration email:");
        log.info("   • Email système (FROM): {}", senderEmail);
        log.info("   • Email destination (TO): {}", contactEmail);
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        try {
            // ✅ EMAIL 1: Notification à l'administrateur
            log.info(" [1/2] Envoi de la notification à l'admin...");
            SimpleMailMessage mailToAdmin = new SimpleMailMessage();
            mailToAdmin.setFrom(senderEmail);
            mailToAdmin.setTo(contactEmail);
            mailToAdmin.setReplyTo(request.getEmail()); // ✅ Important pour répondre directement
            mailToAdmin.setSubject("📬 Nouveau message de contact: " + request.getObjet());
            mailToAdmin.setText(buildAdminEmailBody(request));

            mailSender.send(mailToAdmin);
            log.info(" [1/2] Email admin envoyé avec succès à: {}", contactEmail);

            // EMAIL 2: Confirmation à l'utilisateur
            log.info(" [2/2] Envoi de la confirmation à l'utilisateur...");
            SimpleMailMessage mailToUser = new SimpleMailMessage();
            mailToUser.setFrom(senderEmail);
            mailToUser.setTo(request.getEmail());
            mailToUser.setSubject("✅ Message bien reçu - INE Alumni");
            mailToUser.setText(buildUserEmailBody(request));

            mailSender.send(mailToUser);
            log.info("✅ [2/2] Email de confirmation envoyé à: {}", request.getEmail());

            log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            log.info("🎉 SUCCÈS: Tous les emails ont été envoyés!");
            log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        } catch (Exception e) {
            log.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            log.error("❌ ERREUR lors de l'envoi des emails");
            log.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            log.error("❌ Type d'erreur: {}", e.getClass().getSimpleName());
            log.error("❌ Message d'erreur: {}", e.getMessage());
            log.error("❌ Stack trace:", e);
            log.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            throw new IllegalStateException("Échec d'envoi du message de contact: " + e.getMessage(), e);
        }
    }

    /**
     * Construction du corps de l'email pour l'administrateur
     */
    private String buildAdminEmailBody(ContactMessageRequestDto request) {
        return String.format(
                "Nouveau message depuis le formulaire de contact INE Alumni\n\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"
                        + "INFORMATIONS DE L'EXPÉDITEUR\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n"
                        + "👤 Nom: %s\n"
                        + "👤 Prénom: %s\n"
                        + "📧 Email: %s\n"
                        + "📋 Objet: %s\n\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"
                        + "MESSAGE\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n"
                        + "%s\n\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n"
                        + "💡 Pour répondre à cet utilisateur, cliquez simplement sur\n"
                        + "   'Répondre' dans votre client email.\n\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"
                        + "Email automatique - INE Alumni Platform\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                request.getObjet(),
                request.getMessage()
        );
    }

    /**
     * Construction du corps de l'email pour l'utilisateur
     */
    private String buildUserEmailBody(ContactMessageRequestDto request) {
        return String.format(
                "Bonjour %s %s,\n\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n"
                        + "✅ Nous avons bien reçu votre message!\n\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n"
                        + "Objet de votre message:\n"
                        + "« %s »\n\n"
                        + "Notre équipe examinera votre demande et vous répondra\n"
                        + "dans les plus brefs délais.\n\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n"
                        + "Merci de votre intérêt pour INE Alumni!\n\n"
                        + "Cordialement,\n"
                        + "L'équipe INE Alumni\n\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"
                        + "Cet email a été envoyé automatiquement.\n"
                        + "Merci de ne pas y répondre directement.\n"
                        + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                request.getPrenom(),
                request.getNom(),
                request.getObjet()
        );
    }
}