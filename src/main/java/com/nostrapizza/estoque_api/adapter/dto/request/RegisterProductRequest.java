package com.nostrapizza.estoque_api.adapter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RegisterProductRequest(
        @NotBlank String name,
        @NotBlank String unit,
        @PositiveOrZero @NotNull Float currentQuantity,
        @PositiveOrZero @NotNull Float minQuantity

) {
}
