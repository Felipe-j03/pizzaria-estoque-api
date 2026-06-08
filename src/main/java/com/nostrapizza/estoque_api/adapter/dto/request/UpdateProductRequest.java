package com.nostrapizza.estoque_api.adapter.dto.request;

import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductRequest(

        String name,
        String unit,
        @PositiveOrZero Float minQuantity
) {
}
