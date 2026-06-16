package com.copper.Nasir.Service;

import com.copper.Nasir.DTO.CardRequestDTO;
import com.copper.Nasir.DTO.CardResponseDTO;
import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository repository;

    @Autowired
    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    // POST /cards
    public Card createCard(CardRequestDTO dto) {
        Card card = new Card();
        card.setTitle(dto.title());
        card.setImageUrl(dto.imageUrl() != null ? dto.imageUrl() : "");
        card.setRating(dto.rating() != null ? dto.rating() : 0.0);
        card.setReleaseDate(dto.releaseDate());
        card.setCategory(dto.category());
        card.setSynopsis(dto.synopsis());
        return repository.save(card);
    }

    // GET /cards?category=filmes
    public List<CardResponseDTO> getByCategory(CardCategory category) {
        return repository.findByCategory(category)
                .stream()
                .map(CardResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // GET /cards/search?q=batman&category=filmes
    public List<CardResponseDTO> search(String query, CardCategory category) {
        List<Card> results = (category != null)
                ? repository.findByTitleContainingIgnoreCaseAndCategory(query, category)
                : repository.findByTitleContainingIgnoreCase(query);

        return results.stream()
                .map(CardResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // GET /cards/{id}
    public Optional<CardResponseDTO> getById(UUID id) {
        return repository.findById(id).map(CardResponseDTO::fromEntity);
    }
}
