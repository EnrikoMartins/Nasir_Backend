package com.copper.Nasir.Service;

import com.copper.Nasir.DTO.StatusHistoryDTO;
import com.copper.Nasir.Entity.StatusHistory;
import com.copper.Nasir.Entity.UserCard;
import com.copper.Nasir.Enum.CardStatus;
import com.copper.Nasir.Repository.StatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StatusHistoryService {

    private final StatusHistoryRepository repository;

    @Autowired
    public StatusHistoryService(StatusHistoryRepository repository) {
        this.repository = repository;
    }

    public void recordTransition(UserCard userCard, CardStatus from, CardStatus to) {
        repository.save(new StatusHistory(userCard, from, to));
    }

    public List<StatusHistoryDTO> getHistory(UUID userCardId) {
        return repository
                .findByUserCardIdOrderByChangedAtDesc(userCardId)
                .stream()
                .map(StatusHistoryDTO::fromEntity)
                .collect(Collectors.toList());
    }
}