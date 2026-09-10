package com.nostrapizza.estoque_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nostrapizza.estoque_api.application.port.in.UpdateProductCommand;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.application.service.UpdateProductService;
import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.exception.ProductNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UpdateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductService updateProductService;

    @Test
    void shouldReturnAndSaveWhenProductDontExist() {
        UUID commandId = UUID.randomUUID();
        UpdateProductCommand command = new UpdateProductCommand(commandId, "Mussarela", "kg", 2f);

        Product product = new Product(commandId, LocalDateTime.now(), "Provolone", "un", 20f, 5f, true);
        Product productMock = new Product(commandId, LocalDateTime.now(), "Mock", "kg", 10f, 2f, true);

        when(productRepository.findById(commandId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(productMock);

        Product result = updateProductService.execute(command);
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        Product savedProduct = captor.getValue();

        assertSame(productMock, result);
        assertEquals(command.name(), savedProduct.getName());
        assertEquals(command.unit(), savedProduct.getUnit());
        assertEquals(command.minQuantity(), savedProduct.getMinQuantity());
        assertEquals(product.getCurrentQuantity(), savedProduct.getCurrentQuantity());
        assertTrue(savedProduct.isActive());

    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        UUID commandId = UUID.randomUUID();
        UpdateProductCommand command = new UpdateProductCommand(commandId, "Cebola", "kg", 2f);

        when(productRepository.findById(commandId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            updateProductService.execute(command);
        });

        verify(productRepository, never()).save(any());

    }

}
