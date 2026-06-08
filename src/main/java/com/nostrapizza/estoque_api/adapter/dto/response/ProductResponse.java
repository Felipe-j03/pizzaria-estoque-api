package com.nostrapizza.estoque_api.adapter.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String unit,
        float currentQuantity,
        float minQuantity,
        boolean active,
        LocalDateTime createdAt

) {
}
