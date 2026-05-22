package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.entity.Product;

public interface UpdateProductUseCase {
    Product execute(UpdateProductCommand product);
}
