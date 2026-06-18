package com.copper.Nasir.DTO;

import com.copper.Nasir.Entity.StatusHistory;
import com.copper.Nasir.Enum.CardStatus;

import java.time.LocalDateTime;

public record StatusHistoryDTO(
        CardStatus fromStatus,
        CardStatus toStatus,
        LocalDateTime changedAt
) {
    public static StatusHistoryDTO fromEntity(StatusHistory h) {
        return new StatusHistoryDTO(
                h.getFromStatus(),
                h.getToStatus(),
                h.getChangedAt()
        );
    }
}