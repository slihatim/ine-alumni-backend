package com.ine.backend.services;

import com.ine.backend.dto.ContactMessageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${app.contact.to}")
    private String contactEmail;

    public void sendMessage(ContactMessageRequestDto request) {
        try {
            // Send notification to admin
            SimpleMailMessage mailToAdmin = new SimpleMailMessage();
            mailToAdmin.setFrom(senderEmail);
            mailToAdmin.setTo(contactEmail);
            mailToAdmin.setSubject("New Contact Form Submission: " + request.getSubject());
            mailToAdmin.setText(String.format(
                    "New message from contact form:\n\n" +
                            "Name: %s %s\n" +
                            "Email: %s\n" +
                            "Subject: %s\n\n" +
                            "Message:\n%s",
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getSubject(),
                    request.getMessage()
            ));
            mailSender.send(mailToAdmin);

            // Send confirmation to user
            SimpleMailMessage mailToUser = new SimpleMailMessage();
            mailToUser.setFrom(senderEmail);
            mailToUser.setTo(request.getEmail());
            mailToUser.setSubject("Thank you for contacting INE Alumni");
            mailToUser.setText(String.format(
                    "Hello %s %s,\n\n" +
                            "Thank you for reaching out to us. We have received your message and will respond as soon as possible.\n\n" +
                            "Best regards,\n" +
                            "INE Alumni Team",
                    request.getFirstName(),
                    request.getLastName()
            ));
            mailSender.send(mailToUser);

        } catch (Exception e) {
            throw new IllegalStateException("Failed to send contact message email", e);
        }
    }
}