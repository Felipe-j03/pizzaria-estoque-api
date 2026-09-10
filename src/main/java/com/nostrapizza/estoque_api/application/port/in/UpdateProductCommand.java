package com.nostrapizza.estoque_api.application.port.in;

import java.util.UUID;

public record UpdateProductCommand(
        UUID productId,
        String name,
        String unit,
        float minQuantity
) {
}
