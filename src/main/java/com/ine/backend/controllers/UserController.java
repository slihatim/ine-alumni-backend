package com.ine.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ine.backend.entities.InptUser;
import com.ine.backend.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
  private UserService userService;

  @PostMapping
  public ResponseEntity<InptUser> createUser(@RequestBody @Valid InptUser newUser) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(newUser));
  }

  @GetMapping("/{id}")
  public ResponseEntity<InptUser> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUser(id));
  }

  @GetMapping
  public ResponseEntity<List<InptUser>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
    return ResponseEntity.ok(userService.deleteUser(id));
  }
}
