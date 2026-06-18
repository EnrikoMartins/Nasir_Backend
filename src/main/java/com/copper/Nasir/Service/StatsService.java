package com.copper.Nasir.Service;

import com.copper.Nasir.DTO.StatsResponseDTO;
import com.copper.Nasir.Entity.User;
import com.copper.Nasir.Entity.UserCard;
import com.copper.Nasir.Enum.CardCategory;
import com.copper.Nasir.Enum.CardStatus;
import com.copper.Nasir.Repository.UserCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private final UserCardRepository userCardRepository;

    @Autowired
    public StatsService(UserCardRepository userCardRepository) {
        this.userCardRepository = userCardRepository;
    }

    public StatsResponseDTO getUserStats(User user) {
        List<UserCard> all = userCardRepository.findAllByUserId(user.getId());

        long total       = all.size();
        long planejados  = all.stream().filter(uc -> uc.getStatus() == CardStatus.PLANEJADO).count();
        long emAndamento = all.stream().filter(uc -> uc.getStatus() == CardStatus.EM_ANDAMENTO).count();
        long concluidos  = all.stream().filter(uc -> uc.getStatus() == CardStatus.CONCLUIDO).count();

        Map<CardCategory, Long> byCategory = all.stream()
                .collect(Collectors.groupingBy(
                        uc -> uc.getCard().getCategory(),
                        Collectors.counting()
                ));

        double avgRating = all.stream()
                .filter(uc -> uc.getUserRating() != null)
                .mapToDouble(UserCard::getUserRating)
                .average()
                .orElse(0.0);

        return new StatsResponseDTO(total, planejados, emAndamento, concluidos, byCategory, avgRating);
    }
}