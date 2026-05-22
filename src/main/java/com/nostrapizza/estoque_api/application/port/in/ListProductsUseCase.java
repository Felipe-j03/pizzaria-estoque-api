package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.entity.Product;

import java.util.List;

public interface ListProductsUseCase {
    List<Product> execute();
}
