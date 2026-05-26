package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.CreateProductCommand;
import com.nostrapizza.estoque_api.application.port.in.CreateProductUseCase;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.exception.ProductAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductService implements CreateProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product execute(CreateProductCommand command) {
        if (productRepository.existsByName(command.name())) {
            throw new ProductAlreadyExistsException("Product already exists");
        }
        Product product = new Product();
        product.setName(command.name());
        product.setUnit(command.unit());
        product.setMinQuantity(command.minQuantity());
        product.setCurrentQuantity(command.currentQuantity());
        return productRepository.save(product);
    }
}
