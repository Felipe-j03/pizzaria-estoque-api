package com.nostrapizza.estoque_api.application.port.in;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateProductCommand(
        String name,
        String unit,
        float currentQuantity,
        float minQuantity
) {}
