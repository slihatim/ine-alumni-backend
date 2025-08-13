package com.ine.backend.security;

import com.ine.backend.dto.ApiResponseDto;
import com.ine.backend.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailVerificationController {
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResponseDto<Void>> resendVerification(@RequestParam String email){
        emailVerificationService.resendVerificationToken(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponseDto<Void>> verifyEmail(
            @RequestParam String email,
            @RequestParam String token
    ){
        final User verifiedUser = emailVerificationService.verifyEmail(email, token);
        return ResponseEntity.ok(
                new ApiResponseDto<>("l'émail a été verifié", null, true)
        );
    }
}
