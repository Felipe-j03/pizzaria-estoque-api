package com.nostrapizza.estoque_api.application.port.in;

public record CreateProductCommand(
        String name,
        String unit,
        float currentQuantity,
        float minQuantity
) {
}
