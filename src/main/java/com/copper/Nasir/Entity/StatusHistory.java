package com.copper.Nasir.Entity;

import com.copper.Nasir.Enum.CardStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "status_history")
@Getter
@Setter
@NoArgsConstructor
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_card_id", nullable = false)
    private UserCard userCard;

    @Enumerated(EnumType.STRING)
    private CardStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus toStatus;

    @Column(nullable = false)
    private LocalDateTime changedAt;

    public StatusHistory(UserCard userCard, CardStatus fromStatus, CardStatus toStatus) {
        this.userCard   = userCard;
        this.fromStatus = fromStatus;
        this.toStatus   = toStatus;
        this.changedAt  = LocalDateTime.now();
    }
}