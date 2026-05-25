package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.enums.MovementType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListStockMovementsCommand(
        UUID productId,
        UUID userId,
        MovementType type,
        LocalDateTime start,
        LocalDateTime end
) {
}
