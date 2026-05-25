package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.entity.StockMovement;

public interface RegisterStockMovementUseCase {
    StockMovement execute(RegisterStockMovementCommand command);
}
