package com.ine.backend.controllers;

import com.ine.backend.entities.INE;
import com.ine.backend.services.INEService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ines")
@AllArgsConstructor
public class INEController {
    private INEService ineService;

    @PostMapping
    public ResponseEntity<INE> createINE(@RequestBody @Valid INE newINE){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( ineService.saveINE(newINE) );
    }

    @GetMapping("/{id}")
    public ResponseEntity<INE> getINE(@PathVariable Long id){
        return ResponseEntity.ok( ineService.getINE(id) );
    }

    @GetMapping
    public ResponseEntity<List<INE>> getAllINE(){
        return ResponseEntity.ok( ineService.getAllINEs() );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteINE(@PathVariable Long id){
        return ResponseEntity.ok(ineService.deleteINE(id));
    }
}
