package com.copper.Nasir.Controller;

import com.copper.Nasir.DTO.StatsResponseDTO;
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Exception.UserNotFoundException;
import com.copper.Nasir.Service.StatsService;
import com.copper.Nasir.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService  service;
    private final StatsService statsService;

    @Autowired
    public UserController(UserService service, StatsService statsService) {
        this.service      = service;
        this.statsService = statsService;
    }

    // ── endpoints existentes ─────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id,
                                           @RequestBody User newUser) {
        newUser.setId(id);
        return service.updateUser(newUser)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ── novos endpoints ──────────────────────────────────────────────────────

    // Retorna os dados do usuário autenticado
    @GetMapping("/me")
    public ResponseEntity<User> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

    // Atualiza nome e/ou avatar do usuário autenticado
    // Aceita multipart/form-data para suportar upload de imagem
    @PatchMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> updateMe(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) MultipartFile avatar) {
        return ResponseEntity.ok(service.updateProfile(user, username, avatar));
    }

    // Retorna as estatísticas de consumo do usuário autenticado
    @GetMapping("/me/stats")
    public ResponseEntity<StatsResponseDTO> getMyStats(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(statsService.getUserStats(user));
    }
}