package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.RegisterStockMovementCommand;
import com.nostrapizza.estoque_api.application.port.in.RegisterStockMovementUseCase;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.application.port.out.StockMovementRepository;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.entity.StockMovement;
import com.nostrapizza.estoque_api.domain.entity.User;
import com.nostrapizza.estoque_api.domain.enums.MovementType;
import com.nostrapizza.estoque_api.domain.exception.InsufficientStockException;
import com.nostrapizza.estoque_api.domain.exception.ProductNotFoundException;
import com.nostrapizza.estoque_api.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterStockMovementService implements RegisterStockMovementUseCase {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    @Override
    public StockMovement execute(RegisterStockMovementCommand command) {

        User user = userRepository.findById(command.userId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = productRepository.findById(command.productId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (command.type() == MovementType.OUT) {
            if (command.quantity() > product.getCurrentQuantity()) {
                throw new InsufficientStockException("Insufficient stock");
            } else {
                product.setCurrentQuantity(product.getCurrentQuantity() - command.quantity());
            }
        } else {
            product.setCurrentQuantity(product.getCurrentQuantity() + command.quantity());
        }

        StockMovement stockMovement = new StockMovement();
        stockMovement.setUser(user);
        stockMovement.setProduct(product);
        stockMovement.setQuantity(command.quantity());
        stockMovement.setType(command.type());
        stockMovement.setNote(command.note());
        productRepository.save(product);

        return stockMovementRepository.save(stockMovement);
    }
}
