package com.copper.Nasir.Repository;

import com.copper.Nasir.Entity.UserCard;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Enum.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, UUID>,
        JpaSpecificationExecutor<UserCard> {

    // ── consultas existentes ─────────────────────────────────────────────────

    Optional<UserCard> findByUserIdAndCardId(UUID userId, UUID cardId);

    List<UserCard> findByUserIdAndFavoriteTrue(UUID userId);
    List<UserCard> findByUserIdAndOnListTrue(UUID userId);
    List<UserCard> findByUserIdAndConsumedTrue(UUID userId);

    // ── novas consultas ──────────────────────────────────────────────────────

    // Busca todos os UserCards de um usuário (usado pelo StatsService)
    List<UserCard> findAllByUserId(UUID userId);

    // Listagem paginada sem filtros (base para o histórico de consumo)
    Page<UserCard> findByUserId(UUID userId, Pageable pageable);

    // Queries de agregação para o StatsService
    @Query("SELECT COUNT(uc) FROM UserCard uc WHERE uc.user.id = :userId AND uc.status = :status")
    long countByUserIdAndStatus(@Param("userId") UUID userId,
                                @Param("status") CardStatus status);

    @Query("SELECT uc.card.category, COUNT(uc) FROM UserCard uc WHERE uc.user.id = :userId GROUP BY uc.card.category")
    List<Object[]> countByUserIdGroupByCategory(@Param("userId") UUID userId);
}