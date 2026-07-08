package com.copper.Nasir.Service;

import com.copper.Nasir.DTO.CardResponseDTO;
import com.copper.Nasir.DTO.StatusHistoryDTO;
import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Entity.UserCard;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Enum.CardStatus;
import com.copper.Nasir.Repository.CardRepository;
import com.copper.Nasir.Repository.UserCardRepository;
import com.copper.Nasir.Spec.UserCardSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserCardService {

    private final UserCardRepository    userCardRepository;
    private final CardRepository        cardRepository;
    private final StatusHistoryService  statusHistoryService;

    @Autowired
    public UserCardService(UserCardRepository userCardRepository,
                           CardRepository cardRepository,
                           StatusHistoryService statusHistoryService) {
        this.userCardRepository  = userCardRepository;
        this.cardRepository      = cardRepository;
        this.statusHistoryService = statusHistoryService;
    }

    // ── getters ──────────────────────────────────────────────────────────────

    public List<CardResponseDTO> getFavorites(User user) {
        return userCardRepository.findByUserIdAndFavoriteTrue(user.getId())
                .stream()
                .map(uc -> CardResponseDTO.fromEntity(uc.getCard(), uc))
                .collect(Collectors.toList());
    }

    public List<CardResponseDTO> getList(User user) {
        return userCardRepository.findByUserIdAndOnListTrue(user.getId())
                .stream()
                .map(uc -> CardResponseDTO.fromEntity(uc.getCard(), uc))
                .collect(Collectors.toList());
    }

    public List<CardResponseDTO> getConsumed(User user) {
        return userCardRepository.findByUserIdAndConsumedTrue(user.getId())
                .stream()
                .map(uc -> CardResponseDTO.fromEntity(uc.getCard(), uc))
                .collect(Collectors.toList());
    }

    // ── toggles ──────────────────────────────────────────────────────────────

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

    // ── status ───────────────────────────────────────────────────────────────

    public void updateStatus(User user, UUID cardId, CardStatus newStatus) {
        UserCard uc = getOrCreate(user, cardId);
        CardStatus oldStatus = uc.getStatus();

        if (!oldStatus.equals(newStatus)) {
            // persiste antes de gravar o histórico para garantir FK válida
            userCardRepository.save(uc);
            statusHistoryService.recordTransition(uc, oldStatus, newStatus);
            uc.setStatus(newStatus);
            userCardRepository.save(uc);
        }
    }

    public List<StatusHistoryDTO> getHistory(User user, UUID cardId) {
        UserCard uc = userCardRepository
                .findByUserIdAndCardId(user.getId(), cardId)
                .orElseThrow(() -> new RuntimeException("UserCard não encontrado."));
        return statusHistoryService.getHistory(uc.getId());
    }

    // ── review ───────────────────────────────────────────────────────────────

    public void updateReview(User user, UUID cardId, Double userRating, String comment) {
        UserCard uc = getOrCreate(user, cardId);

        if (uc.getStatus() != CardStatus.CONCLUIDO) {
            throw new IllegalStateException(
                    "A avaliação só pode ser registrada em mídias com status CONCLUIDO.");
        }

        uc.setUserRating(userRating);
        uc.setComment(comment);
        userCardRepository.save(uc);
    }

    // ── exclusão ─────────────────────────────────────────────────────────────

    public void deleteUserCard(User user, UUID cardId) {
        UserCard uc = userCardRepository
                .findByUserIdAndCardId(user.getId(), cardId)
                .orElseThrow(() -> new RuntimeException(
                        "Mídia não encontrada na lista do usuário."));
        // O cascade ALL no mapeamento de statusHistory garante
        // que o histórico é removido junto com o UserCard
        userCardRepository.delete(uc);
    }

    // ── listagem paginada com filtros ─────────────────────────────────────────

    public Page<CardResponseDTO> getUserCards(User user,
                                              int page,
                                              int limit,
                                              String sort,
                                              CardStatus status,
                                              CardCategory category) {

        var pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, sort));

        Specification<UserCard> spec = Specification
                .where(UserCardSpecification.byUserId(user.getId()));

        if (status   != null) spec = spec.and(UserCardSpecification.byStatus(status));
        if (category != null) spec = spec.and(UserCardSpecification.byCategory(category));

        return userCardRepository
                .findAll(spec, pageable)
                .map(uc -> CardResponseDTO.fromEntity(uc.getCard(), uc));
    }

    // ── helper ───────────────────────────────────────────────────────────────

    private UserCard getOrCreate(User user, UUID cardId) {
        return userCardRepository
                .findByUserIdAndCardId(user.getId(), cardId)
                .orElseGet(() -> {
                    Card card = cardRepository.findById(cardId)
                            .orElseThrow(() -> new RuntimeException(
                                    "Card não encontrado: " + cardId));
                    return new UserCard(user, card);
                });
    }
}

/* package com.copper.Nasir.Service;

import com.copper.Nasir.DTO.CardResponseDTO;
import com.copper.Nasir.DTO.StatusHistoryDTO;
import com.copper.Nasir.Entity.Card;
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Entity.UserCard;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Enum.CardStatus;
import com.copper.Nasir.Repository.CardRepository;
import com.copper.Nasir.Repository.UserCardRepository;
import com.copper.Nasir.Specification.UserCardSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserCardService {

    private final UserCardRepository    userCardRepository;
    private final CardRepository        cardRepository;
    private final StatusHistoryService  statusHistoryService;

    @Autowired
    public UserCardService(UserCardRepository userCardRepository,
                           CardRepository cardRepository,
                           StatusHistoryService statusHistoryService) {
        this.userCardRepository  = userCardRepository;
        this.cardRepository      = cardRepository;
        this.statusHistoryService = statusHistoryService;
    }

    // ── getters ──────────────────────────────────────────────────────────────

    public List<CardResponseDTO> getFavorites(User user) {
        return userCardRepository.findByUserIdAndFavoriteTrue(user.getId())
                .stream()
                .map(uc -> CardResponseDTO.fromEntity(uc.getCard(), uc))
                .collect(Collectors.toList());
    }

    public List<CardResponseDTO> getList(User user) {
        return userCardRepository.findByUserIdAndOnListTrue(user.getId())
                .stream()
                .map(uc -> CardResponseDTO.fromEntity(uc.getCard(), uc))
                .collect(Collectors.toList());
    }

    public List<CardResponseDTO> getConsumed(User user) {
        return userCardRepository.findByUserIdAndConsumedTrue(user.getId())
                .stream()
                .map(uc -> CardResponseDTO.fromEntity(uc.getCard(), uc))
                .collect(Collectors.toList());
    }

    // ── toggles ──────────────────────────────────────────────────────────────

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

    // ── status ───────────────────────────────────────────────────────────────

    public void updateStatus(User user, UUID cardId, CardStatus newStatus) {
        UserCard uc = getOrCreate(user, cardId);
        CardStatus oldStatus = uc.getStatus();

        if (!oldStatus.equals(newStatus)) {
            userCardRepository.save(uc);
            statusHistoryService.recordTransition(uc, oldStatus, newStatus);
            uc.setStatus(newStatus);
            userCardRepository.save(uc);
        }
    }

    public List<StatusHistoryDTO> getHistory(User user, UUID cardId) {
        UserCard uc = userCardRepository
                .findByUserIdAndCardId(user.getId(), cardId)
                .orElseThrow(() -> new RuntimeException("UserCard não encontrado."));
        return statusHistoryService.getHistory(uc.getId());
    }

    // ── review ───────────────────────────────────────────────────────────────

    public void updateReview(User user, UUID cardId, Double userRating, String comment) {
        UserCard uc = getOrCreate(user, cardId);

        if (uc.getStatus() != CardStatus.CONCLUIDO) {
            throw new IllegalStateException(
                    "A avaliação só pode ser registrada em mídias com status CONCLUIDO.");
        }
        if (userRating != null && (userRating < 1.0 || userRating > 10.0)) {
        throw new IllegalArgumentException("A nota de avaliação deve estar entre 1 e 10.");
    }

        uc.setUserRating(userRating);
        uc.setComment(comment);
        userCardRepository.save(uc);
    }

    // ── exclusão ─────────────────────────────────────────────────────────────

    public void deleteUserCard(User user, UUID cardId) {
        UserCard uc = userCardRepository
                .findByUserIdAndCardId(user.getId(), cardId)
                .orElseThrow(() -> new RuntimeException(
                        "Mídia não encontrada na lista do usuário."));
        userCardRepository.delete(uc);
    }

    // ── listagem paginada com filtros ─────────────────────────────────────────

    public Page<CardResponseDTO> getUserCards(User user,
                                              int page,
                                              int limit,
                                              String sort,
                                              CardStatus status,
                                              CardCategory category,
                                              Double rating) { // <-- Parâmetro adicionado

        var pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, sort));

        Specification<UserCard> spec = Specification
                .where(UserCardSpecification.byUserId(user.getId()));

        if (status   != null) spec = spec.and(UserCardSpecification.byStatus(status));
        if (category != null) spec = spec.and(UserCardSpecification.byCategory(category));
        if (rating   != null) spec = spec.and(UserCardSpecification.byMinRating(rating)); // <-- Filtro adicionado

        return userCardRepository
                .findAll(spec, pageable)
                .map(uc -> CardResponseDTO.fromEntity(uc.getCard(), uc)); // <-- Reutilizando o mapper correto do seu colega
    }

    // ── helper ───────────────────────────────────────────────────────────────

    private UserCard getOrCreate(User user, UUID cardId) {
        return userCardRepository
                .findByUserIdAndCardId(user.getId(), cardId)
                .orElseGet(() -> {
                    Card card = cardRepository.findById(cardId)
                            .orElseThrow(() -> new RuntimeException(
                                    "Card não encontrado: " + cardId));
                    return new UserCard(user, card);
                });
    }
}*/
