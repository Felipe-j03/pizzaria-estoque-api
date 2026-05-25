package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.enums.MovementType;

import java.util.UUID;

public record RegisterStockMovementCommand(
        UUID productId,
        UUID userId,
        float quantity,
        MovementType type,
        String note
) {
}
