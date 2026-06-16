package com.copper.Nasir.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "user_cards",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "card_id"})
)
@Getter
@Setter
@NoArgsConstructor
public class UserCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    private boolean favorite = false;
    private boolean onList   = false;
    private boolean consumed = false;

    public UserCard(User user, Card card) {
        this.user = user;
        this.card = card;
    }
}
