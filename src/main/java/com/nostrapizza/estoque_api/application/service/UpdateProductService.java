package com.nostrapizza.estoque_api.application.service;


import com.nostrapizza.estoque_api.application.port.in.UpdateProductCommand;
import com.nostrapizza.estoque_api.application.port.in.UpdateProductUseCase;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.exception.ProductAlreadyExistsException;
import com.nostrapizza.estoque_api.domain.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductService implements UpdateProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product execute(UpdateProductCommand command) {

        Product product = productRepository
                .findById(command.productId())
                .orElseThrow(() ->  new ProductNotFoundException("Product not found: "  + command.productId()));
        product.setName(command.name());
        product.setUnit(command.unit());
        product.setMinQuantity(command.minQuantity());

        return productRepository.save(product);
    }
}
