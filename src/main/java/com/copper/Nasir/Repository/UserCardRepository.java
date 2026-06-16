package com.copper.Nasir.Repository;

import com.copper.Nasir.Entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, UUID> {

    Optional<UserCard> findByUserIdAndCardId(UUID userId, UUID cardId);

    List<UserCard> findByUserIdAndFavoriteTrue(UUID userId);
    List<UserCard> findByUserIdAndOnListTrue(UUID userId);
    List<UserCard> findByUserIdAndConsumedTrue(UUID userId);
}
