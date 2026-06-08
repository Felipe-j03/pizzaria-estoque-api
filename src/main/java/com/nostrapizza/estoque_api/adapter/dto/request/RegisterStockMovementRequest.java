package com.nostrapizza.estoque_api.adapter.dto.request;

import com.nostrapizza.estoque_api.domain.enums.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record RegisterStockMovementRequest(

        @NotNull UUID productId,
        @Positive @NotNull Float quantity,
        @NotNull MovementType type,
        String note
) {
}
