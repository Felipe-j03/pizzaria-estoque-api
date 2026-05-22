package com.nostrapizza.estoque_api.application.port.out;

import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.entity.StockMovement;
import com.nostrapizza.estoque_api.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockMovementRepository {

    StockMovement save(StockMovement stockMovement);

    Optional<StockMovement> findById(UUID id);

    List<StockMovement> findAll();

    List<StockMovement> findByPeriod(LocalDateTime start, LocalDateTime end);
}
