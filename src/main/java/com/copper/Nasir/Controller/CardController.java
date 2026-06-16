package com.copper.Nasir.Controller;

import com.copper.Nasir.DTO.CardRequestDTO;
import com.copper.Nasir.DTO.CardResponseDTO;
import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService service;

    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@Valid @RequestBody CardRequestDTO cardDTO) {
        Card savedCard = service.createCard(cardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(CardResponseDTO.fromEntity(savedCard));
    }

    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> getByCategory(
            @RequestParam CardCategory category) {
        return ResponseEntity.ok(service.getByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CardResponseDTO>> search(
            @RequestParam String q,
            @RequestParam(required = false) CardCategory category) {
        return ResponseEntity.ok(service.search(q, category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
