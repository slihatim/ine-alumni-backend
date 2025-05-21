package com.ine.backend.controllers;

import com.ine.backend.dto.SignUpRequestDto;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerIne(@RequestBody @Valid SignUpRequestDto requestDto)
        throws UserAlreadyExistsException {

        authService.signUpUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Le compte utilisateur a été créé avec succès !");
    }
}
