package com.ine.backend.services;

import com.ine.backend.entities.INE;
import org.springframework.stereotype.Service;

import java.util.List;

public interface INEService {
    public INE getINE(Long id);
    public List<INE> getAllINEs();
    public INE saveINE(INE ine);
    public Long deleteINE(Long id);

    boolean existsByEmail(String email);
}
