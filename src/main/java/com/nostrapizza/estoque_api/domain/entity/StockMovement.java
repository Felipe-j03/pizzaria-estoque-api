package com.nostrapizza.estoque_api.domain.entity;

import com.nostrapizza.estoque_api.domain.enums.MovementType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class StockMovement {

    private final UUID id;
    private final LocalDateTime createdAt;
    @Setter Product product;
    @Setter User user;
    @Setter float quantity;
    @Setter String note;
    @Setter MovementType type;

    public StockMovement() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}
