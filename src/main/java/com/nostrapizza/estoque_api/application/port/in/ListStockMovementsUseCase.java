package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.entity.StockMovement;

import java.util.List;

public interface ListStockMovementsUseCase {
    List<StockMovement> execute(ListStockMovementsCommand command);
}
