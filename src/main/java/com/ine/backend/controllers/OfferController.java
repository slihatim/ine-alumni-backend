package com.ine.backend.controllers;

import com.ine.backend.dto.OfferRequestDto;
import com.ine.backend.dto.OfferResponseDto;
import com.ine.backend.entities.InptUser;
import com.ine.backend.services.OfferService;
import com.ine.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offers")
@AllArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getOffers() {
        return ResponseEntity.ok(offerService.getAllOffers());
    }

    @PostMapping
    public ResponseEntity<OfferResponseDto> createOffer(@RequestBody @Valid OfferRequestDto requestDto) {
        OfferResponseDto created = offerService.createOffer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> applyToOffer(@PathVariable("id") Long offerId,
                                             @RequestParam("userId") Long userId,
                                             @RequestBody(required = false) ApplicationRequest body) {
        InptUser user = userService.getUser(userId);
        String message = body != null ? body.getMessage() : null;
        String resumeUrl = body != null ? body.getResumeUrl() : null;
        offerService.applyToOffer(offerId, user, message, resumeUrl);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Data
    private static class ApplicationRequest {
        private String message;
        private String resumeUrl;
    }
}

