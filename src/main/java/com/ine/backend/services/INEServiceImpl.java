package com.ine.backend.services;

import com.ine.backend.entities.INE;
import com.ine.backend.repositories.INERepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class INEServiceImpl implements INEService {

    private INERepository ineRepository;

    @Override
    public INE getINE(Long id) {
        return ineRepository.findById(id).get();
    }

    @Override
    public List<INE> getAllINEs() {
        return ineRepository.findAll();
    }

    @Override
    public INE saveINE(INE ine) {
        return ineRepository.save(ine);
    }

    @Override
    public Long deleteINE(Long id) {
        ineRepository.deleteById(id);
        return id;
    }
}
