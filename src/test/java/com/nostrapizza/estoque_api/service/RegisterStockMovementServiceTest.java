package com.nostrapizza.estoque_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nostrapizza.estoque_api.application.port.in.RegisterStockMovementCommand;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.application.port.out.StockMovementRepository;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.application.service.CreateUserService;
import com.nostrapizza.estoque_api.application.service.RegisterStockMovementService;
import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.entity.StockMovement;
import com.nostrapizza.estoque_api.domain.entity.User;
import com.nostrapizza.estoque_api.domain.enums.MovementType;
import com.nostrapizza.estoque_api.domain.enums.UserRole;
import com.nostrapizza.estoque_api.domain.exception.InsufficientStockException;
import com.nostrapizza.estoque_api.domain.exception.ProductNotFoundException;
import com.nostrapizza.estoque_api.domain.exception.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class RegisterStockMovementServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockMovementRepository stockMovementRepository;

    @InjectMocks
    private RegisterStockMovementService registerStockMovementService;

    @InjectMocks
    private CreateUserService createUserService;

    @Test
    void shouldIncreaseStockWhenMovementIsIn() {

        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Felipe", "felipe@email.com", "hash", UserRole.MANAGER, true, LocalDateTime.now());

        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, LocalDateTime.now(), "Mussarela", "kg", 10f, 2f, true);

        RegisterStockMovementCommand command = new RegisterStockMovementCommand(productId, userId, 5, MovementType.IN,
                null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        UUID stockMovementID = UUID.randomUUID();
        StockMovement savedMovement = new StockMovement(stockMovementID, LocalDateTime.now(), product, user, 15f, null,
                MovementType.IN);
        when(stockMovementRepository.save(any())).thenReturn(savedMovement);

        StockMovement result = registerStockMovementService.execute(command);

        assertNotNull(result);
        assertEquals(15, product.getCurrentQuantity());

    }

    @Test
    void shouldDecreaseStockWhenMovementIsOut() {

        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Felipe", "felipe@email.com", "hash", UserRole.MANAGER, true, LocalDateTime.now());

        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, LocalDateTime.now(), "Mussarela", "kg", 10f, 2f, true);

        RegisterStockMovementCommand command = new RegisterStockMovementCommand(productId, userId, 5, MovementType.OUT,
                null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        UUID stockMovementID = UUID.randomUUID();
        StockMovement savedMovement = new StockMovement(stockMovementID, LocalDateTime.now(), product, user, 10f, null,
                MovementType.OUT);
        when(stockMovementRepository.save(any())).thenReturn(savedMovement);

        StockMovement result = registerStockMovementService.execute(command);

        assertNotNull(result);
        assertEquals(5, product.getCurrentQuantity());
    }

    @Test
    void shouldThrowWhenUserNotFound() {

        UUID userId = UUID.randomUUID();

        UUID productId = UUID.randomUUID();

        RegisterStockMovementCommand command = new RegisterStockMovementCommand(productId, userId, 5, MovementType.OUT,
                null);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            registerStockMovementService.execute(command);
        });
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        UUID userId = UUID.randomUUID();

        UUID productId = UUID.randomUUID();

        RegisterStockMovementCommand command = new RegisterStockMovementCommand(productId, userId, 5, MovementType.OUT,
                null);

        User user = new User(userId, "Felipe", "Felipe@email.com", "has", UserRole.MANAGER, true, LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            registerStockMovementService.execute(command);
        });
    }

    @Test
    void shouldThrowWhenInsufficientStock() {
        UUID userId = UUID.randomUUID();

        UUID productId = UUID.randomUUID();

        RegisterStockMovementCommand command = new RegisterStockMovementCommand(productId, userId, 20, MovementType.OUT,
                null);

        User user = new User(userId, "Felipe", "Felipe@email.com", "has", UserRole.MANAGER, true, LocalDateTime.now());
        Product product = new Product(productId, LocalDateTime.now(), "Mussarela", "kg", 10f, 2f, true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> {
            registerStockMovementService.execute(command);
        });
    }

}
