package com.copper.Nasir.Controller;

import com.copper.Nasir.DTO.CardResponseDTO;
import com.copper.Nasir.DTO.ReviewRequestDTO;
import com.copper.Nasir.DTO.StatusHistoryDTO;
import com.copper.Nasir.DTO.StatusUpdateDTO;
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Enum.CardStatus;
import com.copper.Nasir.Service.UserCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    // ── endpoints existentes ─────────────────────────────────────────────────

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

    // ── novos endpoints ──────────────────────────────────────────────────────

    // 1. Listagem paginada com filtros opcionais
    @GetMapping("/cards")
    public ResponseEntity<Page<CardResponseDTO>> getUserCards(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0")         int page,
            @RequestParam(defaultValue = "20")        int limit,
            @RequestParam(defaultValue = "updatedAt") String sort,
            @RequestParam(required = false)           CardStatus status,
            @RequestParam(required = false)           CardCategory category) {
        return ResponseEntity.ok(
                userCardService.getUserCards(user, page, limit, sort, status, category));
    }

    // 2. Atualizar status da mídia
    @PatchMapping("/cards/{cardId}/status")
    public ResponseEntity<Void> updateStatus(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId,
            @Valid @RequestBody StatusUpdateDTO dto) {
        userCardService.updateStatus(user, cardId, dto.status());
        return ResponseEntity.ok().build();
    }

    // 3. Registrar avaliação (só disponível se status = CONCLUIDO)
    @PatchMapping("/cards/{cardId}/review")
    public ResponseEntity<Void> updateReview(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId,
            @Valid @RequestBody ReviewRequestDTO dto) {
        userCardService.updateReview(user, cardId, dto.userRating(), dto.comment());
        return ResponseEntity.ok().build();
    }

    // 4. Histórico de mudanças de status
    @GetMapping("/cards/{cardId}/history")
    public ResponseEntity<List<StatusHistoryDTO>> getHistory(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId) {
        return ResponseEntity.ok(userCardService.getHistory(user, cardId));
    }

    // 5. Excluir mídia da lista do usuário
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<Void> deleteUserCard(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId) {
        userCardService.deleteUserCard(user, cardId);
        return ResponseEntity.noContent().build();
    }
}

/* package com.copper.Nasir.Controller;

import com.copper.Nasir.DTO.CardResponseDTO;
import com.copper.Nasir.DTO.ReviewRequestDTO;
import com.copper.Nasir.DTO.StatusHistoryDTO;
import com.copper.Nasir.DTO.StatusUpdateDTO;
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Enum.CardStatus;
import com.copper.Nasir.Service.UserCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    // ── endpoints existentes ─────────────────────────────────────────────────

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

    // ── novos endpoints ──────────────────────────────────────────────────────

    // 1. Listagem paginada com filtros opcionais (Atualizado com o filtro de Avaliação/Rating)
    @GetMapping("/cards")
    public ResponseEntity<Page<CardResponseDTO>> getUserCards(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0")           int page,
            @RequestParam(defaultValue = "20")          int limit,
            @RequestParam(defaultValue = "updatedAt") String sort,
            @RequestParam(required = false)           CardStatus status,
            @RequestParam(required = false)           CardCategory category,
            @RequestParam(required = false)           Double rating) { // <-- Parâmetro de avaliação adicionado com sucesso
        return ResponseEntity.ok(
                userCardService.getUserCards(user, page, limit, sort, status, category, rating));
    }

    // 2. Atualizar status da mídia
    @PatchMapping("/cards/{cardId}/status")
    public ResponseEntity<Void> updateStatus(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId,
            @Valid @RequestBody StatusUpdateDTO dto) {
        userCardService.updateStatus(user, cardId, dto.status());
        return ResponseEntity.ok().build();
    }

    // 3. Registrar avaliação (só disponível se status = CONCLUIDO)
    @PatchMapping("/cards/{cardId}/review")
    public ResponseEntity<Void> updateReview(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId,
            @Valid @RequestBody ReviewRequestDTO dto) {
        userCardService.updateReview(user, cardId, dto.userRating(), dto.comment());
        return ResponseEntity.ok().build();
    }

    // 4. Histórico de mudanças de status
    @GetMapping("/cards/{cardId}/history")
    public ResponseEntity<List<StatusHistoryDTO>> getHistory(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId) {
        return ResponseEntity.ok(userCardService.getHistory(user, cardId));
    }

    // 5. Excluir mídia da lista do usuário
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<Void> deleteUserCard(
            @AuthenticationPrincipal User user,
            @PathVariable UUID cardId) {
        userCardService.deleteUserCard(user, cardId);
        return ResponseEntity.noContent().build();
    }
} */
