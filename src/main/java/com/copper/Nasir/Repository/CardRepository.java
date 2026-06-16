package com.copper.Nasir.Repository;

import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Enum.CardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    // GET /cards?category=filmes
    List<Card> findByCategory(CardCategory category);

    // GET /cards/search?q=batman&category=filmes  (com filtro)
    List<Card> findByTitleContainingIgnoreCaseAndCategory(String title, CardCategory category);

    // GET /cards/search?q=batman  (sem filtro de categoria)
    List<Card> findByTitleContainingIgnoreCase(String title);
}
