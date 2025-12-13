package com.ine.backend.controllers;

import com.ine.backend.dto.ContactMessageRequestDto;
import com.ine.backend.services.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping
    public ResponseEntity<String> sendContactMessage(@Valid @RequestBody ContactMessageRequestDto request) {
        contactMessageService.sendMessage(request);
        return ResponseEntity.ok("Message sent successfully");
    }
}