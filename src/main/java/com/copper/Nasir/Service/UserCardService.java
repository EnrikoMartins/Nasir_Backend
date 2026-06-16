package com.copper.Nasir.Service;

import com.copper.Nasir.DTO.CardResponseDTO;
import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Entity.UserCard;
import com.copper.Nasir.Repository.CardRepository;
import com.copper.Nasir.Repository.UserCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserCardService {

    private final UserCardRepository userCardRepository;
    private final CardRepository cardRepository;

    @Autowired
    public UserCardService(UserCardRepository userCardRepository, CardRepository cardRepository) {
        this.userCardRepository = userCardRepository;
        this.cardRepository = cardRepository;
    }

    // --- Getters ---

    public List<CardResponseDTO> getFavorites(User user) {
        return userCardRepository.findByUserIdAndFavoriteTrue(user.getId())
                .stream().map(uc -> CardResponseDTO.fromEntity(uc.getCard()))
                .collect(Collectors.toList());
    }

    public List<CardResponseDTO> getList(User user) {
        return userCardRepository.findByUserIdAndOnListTrue(user.getId())
                .stream().map(uc -> CardResponseDTO.fromEntity(uc.getCard()))
                .collect(Collectors.toList());
    }

    public List<CardResponseDTO> getConsumed(User user) {
        return userCardRepository.findByUserIdAndConsumedTrue(user.getId())
                .stream().map(uc -> CardResponseDTO.fromEntity(uc.getCard()))
                .collect(Collectors.toList());
    }

    // --- Toggles ---

    public void toggleFavorite(User user, UUID cardId) {
        UserCard uc = getOrCreate(user, cardId);
        uc.setFavorite(!uc.isFavorite());
        userCardRepository.save(uc);
    }

    public void toggleList(User user, UUID cardId) {
        UserCard uc = getOrCreate(user, cardId);
        uc.setOnList(!uc.isOnList());
        userCardRepository.save(uc);
    }

    public void toggleConsumed(User user, UUID cardId) {
        UserCard uc = getOrCreate(user, cardId);
        uc.setConsumed(!uc.isConsumed());
        userCardRepository.save(uc);
    }

    // --- Helper ---

    private UserCard getOrCreate(User user, UUID cardId) {
        return userCardRepository.findByUserIdAndCardId(user.getId(), cardId)
                .orElseGet(() -> {
                    Card card = cardRepository.findById(cardId)
                            .orElseThrow(() -> new RuntimeException("Card não encontrado: " + cardId));
                    return new UserCard(user, card);
                });
    }
}
