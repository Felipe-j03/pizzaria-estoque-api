package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.ListStockMovementsCommand;
import com.nostrapizza.estoque_api.application.port.in.ListStockMovementsUseCase;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.application.port.out.StockMovementRepository;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.domain.entity.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListStockMovementsService implements ListStockMovementsUseCase {

    private final StockMovementRepository stockMovementRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<StockMovement> execute(ListStockMovementsCommand command) {
        List<StockMovement> stockMovements;

        if (command.start() != null && command.end() != null) {
            stockMovements = stockMovementRepository.findByPeriod(command.start(), command.end());
        } else {
            stockMovements = stockMovementRepository.findAll();
        }

        return stockMovements.stream()
                .filter(movement -> command.productId() == null || movement.getProduct().getId().equals(command.productId()))
                .filter(movement -> command.userId() == null || movement.getUser().getId().equals(command.userId()))
                .filter(movement -> command.type() == null || movement.getType() == command.type())
                .toList();
    }
}
