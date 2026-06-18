package com.copper.Nasir.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record ReviewRequestDTO(
        @NotNull
        @DecimalMin("1.0")
        @DecimalMax("10.0")
        Double userRating,

        String comment
) {}