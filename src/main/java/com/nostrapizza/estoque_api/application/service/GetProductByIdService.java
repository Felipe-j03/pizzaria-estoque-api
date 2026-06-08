package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.GetProductByIdUseCase;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProductByIdService implements GetProductByIdUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product execute(UUID productId) {

        return productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + productId + " not found")
        );
    }
}
