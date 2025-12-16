package com.ine.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.dto.ContactMessageRequestDto;
import com.ine.backend.services.ContactMessageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/contact")
    public ResponseEntity<?> sendContactMessage(
            @Valid @RequestBody ContactMessageRequestDto request,
            BindingResult bindingResult) {

        log.info("📬 Received contact message request from: {} {}",
                request.getPrenom(), request.getNom());

        // Validation des erreurs
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            log.warn("❌ Validation errors: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            contactMessageService.sendMessage(request);
            log.info("✅ Contact message processed successfully");

            Map<String, String> response = new HashMap<>();
            response.put("message", "Message envoyé avec succès");
            response.put("status", "success");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("❌ Error processing contact message: {}", e.getMessage(), e);

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erreur lors de l'envoi du message");
            errorResponse.put("status", "error");
            errorResponse.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}