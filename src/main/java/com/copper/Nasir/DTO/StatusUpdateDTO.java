package com.copper.Nasir.DTO;

import com.copper.Nasir.Enum.CardStatus;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateDTO(
        @NotNull CardStatus status
) {}