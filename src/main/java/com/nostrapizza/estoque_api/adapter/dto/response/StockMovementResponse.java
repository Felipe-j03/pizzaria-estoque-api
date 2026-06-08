package com.nostrapizza.estoque_api.adapter.dto.response;

import com.nostrapizza.estoque_api.domain.enums.MovementType;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockMovementResponse(
        UUID id,
        UUID productId,
        UUID userId,
        Float quantity,
        MovementType type,
        String note,
        LocalDateTime createdAt
) {
}
