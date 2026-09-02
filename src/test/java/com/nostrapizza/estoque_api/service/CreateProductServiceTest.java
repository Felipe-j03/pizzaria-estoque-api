package com.nostrapizza.estoque_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nostrapizza.estoque_api.application.port.in.CreateProductCommand;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.application.service.CreateProductService;
import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.exception.ProductAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductService createProductService;

    @Test
    void shouldReturnAndSaveWhenProductDontExist() {
        CreateProductCommand command = new CreateProductCommand("Mussarela", "kg", 10f, 2f);

        when(productRepository.existsByName("Mussarela")).thenReturn(false);

        Product product = new Product();
        product.setName("Mussarela");
        product.setUnit("kg");
        product.setCurrentQuantity(10f);
        product.setMinQuantity(2f);
        product.setActive(true);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = createProductService.execute(command);
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        Product savedProduct = captor.getValue();

        assertSame(product, result);
        assertEquals(command.name(), savedProduct.getName());
        assertEquals(command.unit(), savedProduct.getUnit());
        assertEquals(command.currentQuantity(), savedProduct.getCurrentQuantity());
        assertEquals(command.minQuantity(), savedProduct.getMinQuantity());
        assertTrue(savedProduct.isActive());
    }

    @Test
    void shouldThrowExceptionWhenProductAlreadyExists() {
        CreateProductCommand command = new CreateProductCommand("Mussarela", "kg", 12f, 4f);

        when(productRepository.existsByName("Mussarela")).thenReturn(true);

        assertThrows(ProductAlreadyExistsException.class, () -> {
            createProductService.execute(command);
        });

        verify(productRepository, never()).save(any());
    }
}
