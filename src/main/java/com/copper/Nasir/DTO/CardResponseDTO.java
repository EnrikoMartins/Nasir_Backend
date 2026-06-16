package com.copper.Nasir.DTO;

import com.copper.Nasir.Enum.CardCategory;
import java.util.UUID;

public record CardResponseDTO(
        UUID id,
        String title,
        String imageUrl,
        Double rating,
        String releaseDate,
        CardCategory category,
        String synopsis
) {

    public static CardResponseDTO fromEntity(com.copper.Nasir.Entity.Card card) {
        return new CardResponseDTO(
                card.getId(),
                card.getTitle(),
                card.getImageUrl(),
                card.getRating(),
                card.getReleaseDate(),
                card.getCategory(),
                card.getSynopsis()
        );
    }
}