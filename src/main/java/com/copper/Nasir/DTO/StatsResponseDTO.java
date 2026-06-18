package com.copper.Nasir.DTO;

import com.copper.Nasir.Enum.CardCategory;

import java.util.Map;

public record StatsResponseDTO(
        long total,
        long planejados,
        long emAndamento,
        long concluidos,
        Map<CardCategory, Long> byCategory,
        double averageUserRating
) {}