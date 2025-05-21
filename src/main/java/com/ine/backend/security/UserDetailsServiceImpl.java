package com.ine.backend.security;

import com.ine.backend.entities.INE;
import com.ine.backend.repositories.INERepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private INERepository ineRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        INE ine = ineRepository.findByEmail(username);
        if(ine == null) {
            throw new UsernameNotFoundException("User not found with email: "+username);
        }
        return UserDetailsImpl.build(ine);
    }
}
