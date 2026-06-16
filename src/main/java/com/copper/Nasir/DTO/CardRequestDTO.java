package com.copper.Nasir.DTO;

import com.copper.Nasir.Enum.CardCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CardRequestDTO(
        @NotBlank(message = "O título é obrigatório") String title,
        String imageUrl,
        Double rating,
        String releaseDate,
        @NotNull(message = "A categoria é obrigatória") CardCategory category,
        String synopsis
) {}

