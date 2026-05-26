package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.ListLowStockProductsUseCase;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListLowStockProductsService implements ListLowStockProductsUseCase {

    private final ProductRepository productRepository;

    @Override
    public List<Product> execute() {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getCurrentQuantity() < product.getMinQuantity())
                .toList();
    }
}
