package com.ine.backend.services;

import com.ine.backend.dto.SignUpRequestDto;
import com.ine.backend.entities.INE;
import com.ine.backend.exceptions.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private INEService ineService;
    private PasswordEncoder passwordEncoder;

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
}
