package com.ine.backend.services;

import com.ine.backend.dto.SignInRequestDto;
import com.ine.backend.dto.SignInResponseDto;
import com.ine.backend.dto.SignUpRequestDto;
import com.ine.backend.entities.INE;
import com.ine.backend.entities.Role;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import com.ine.backend.security.UserDetailsImpl;
import com.ine.backend.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {
    private INEService ineService;
    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public void signUpUser(SignUpRequestDto requestDto) throws UserAlreadyExistsException{
        if(ineService.existsByEmail(requestDto.getEmail())){
            throw new UserAlreadyExistsException("Échec d'inscription : l'email fourni existe déjà. Essayez de vous connecter ou utilisez un autre email.");
        }

        INE ine = createIne(requestDto);
        ineService.saveINE(ine);

    }

    private INE createIne(SignUpRequestDto requestDto){
        INE ine = new INE();

        ine.setFullName(requestDto.getFullName());
        ine.setEmail(requestDto.getEmail());
        ine.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        ine.setMajor(requestDto.getMajor());
        ine.setGraduationYear(requestDto.getGraduationYear());
        ine.setPhoneNumber(requestDto.getPhoneNumber());
        ine.setBirthDate(requestDto.getBirthDate());
        ine.setGender(requestDto.getGender());
        ine.setLinkedinUrl(requestDto.getLinkedinUrl());
        ine.setCountry(requestDto.getCountry());
        ine.setCity(requestDto.getCity());

        return ine;
    }

    public SignInResponseDto signInUser(SignInRequestDto requestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        SignInResponseDto signInResponseDto = SignInResponseDto.builder()
                .email(userDetails.getUsername())
                .token(jwt)
                .type("Bearer")
                .role(Role.valueOf(roles.get(0)))
                .build();

        return signInResponseDto;
    }
}
