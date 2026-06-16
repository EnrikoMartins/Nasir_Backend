package com.copper.Nasir.Controller;

import com.copper.Nasir.DTO.CardResponseDTO;
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Service.UserCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserCardController {

    private final UserCardService userCardService;

    @Autowired
    public UserCardController(UserCardService userCardService) {
        this.userCardService = userCardService;
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<CardResponseDTO>> getFavorites(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userCardService.getFavorites(user));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CardResponseDTO>> getList(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userCardService.getList(user));
    }

    @GetMapping("/consumed")
    public ResponseEntity<List<CardResponseDTO>> getConsumed(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userCardService.getConsumed(user));
    }

    @PostMapping("/favorites/{cardId}")
    public ResponseEntity<Void> toggleFavorite(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId) {
        userCardService.toggleFavorite(user, cardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list/{cardId}")
    public ResponseEntity<Void> toggleList(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId) {
        userCardService.toggleList(user, cardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/consumed/{cardId}")
    public ResponseEntity<Void> toggleConsumed(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId) {
        userCardService.toggleConsumed(user, cardId);
        return ResponseEntity.ok().build();
    }
}
