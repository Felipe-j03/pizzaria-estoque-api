package com.nostrapizza.estoque_api.application.port.out;

import com.nostrapizza.estoque_api.domain.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    boolean existsByName(String name);
}
