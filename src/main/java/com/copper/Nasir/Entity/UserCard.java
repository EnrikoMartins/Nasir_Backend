package com.copper.Nasir.Entity;

import com.copper.Nasir.Enum.CardStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    // ── novos campos ─────────────────────────────────────────────────────────

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status = CardStatus.PLANEJADO;

    private Double userRating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // cascade ALL + orphanRemoval garante deleção automática do histórico
    // quando o UserCard for removido
    @OneToMany(
            mappedBy    = "userCard",
            cascade     = CascadeType.ALL,
            orphanRemoval = true,
            fetch       = FetchType.LAZY
    )
    private List<StatusHistory> statusHistory = new ArrayList<>();

    // ── construtores ─────────────────────────────────────────────────────────

    public UserCard(User user, Card card) {
        this.user   = user;
        this.card   = card;
        this.status = CardStatus.PLANEJADO;
    }
}