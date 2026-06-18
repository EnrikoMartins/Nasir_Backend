package com.copper.Nasir.DTO;

import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Entity.UserCard;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Enum.CardStatus;

import java.util.UUID;

public record CardResponseDTO(
        UUID         id,
        String       title,
        String       imageUrl,
        Double       rating,
        String       releaseDate,
        CardCategory category,
        String       synopsis,
        // campos do usuário — nulos nos endpoints públicos de catálogo
        CardStatus   status,
        Double       userRating,
        String       comment
) {

    // Usado nos endpoints públicos: GET /cards, GET /cards/search, GET /cards/{id}
    public static CardResponseDTO fromEntity(Card card) {
        return new CardResponseDTO(
                card.getId(),
                card.getTitle(),
                card.getImageUrl(),
                card.getRating(),
                card.getReleaseDate(),
                card.getCategory(),
                card.getSynopsis(),
                null, null, null
        );
    }

    // Usado nos endpoints do usuário: /user/favorites, /user/cards, etc.
    public static CardResponseDTO fromEntity(Card card, UserCard uc) {
        return new CardResponseDTO(
                card.getId(),
                card.getTitle(),
                card.getImageUrl(),
                card.getRating(),
                card.getReleaseDate(),
                card.getCategory(),
                card.getSynopsis(),
                uc.getStatus(),
                uc.getUserRating(),
                uc.getComment()
        );
    }
}