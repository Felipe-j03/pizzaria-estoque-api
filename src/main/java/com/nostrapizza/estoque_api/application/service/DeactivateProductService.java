package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.DeactivateProductUseCase;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeactivateProductService implements DeactivateProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public void execute(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
        product.setActive(false);
        productRepository.save(product);
    }
}
