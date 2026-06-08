package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.entity.Product;

import java.util.UUID;

public interface GetProductByIdUseCase {
    Product execute(UUID productId);
}