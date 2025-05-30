package com.ine.backend.dto;

import com.ine.backend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {
    private String email;
    private String token;
    private String type;
    private Role role;
}
