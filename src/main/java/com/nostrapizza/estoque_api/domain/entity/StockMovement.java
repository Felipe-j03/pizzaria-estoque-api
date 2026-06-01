package com.nostrapizza.estoque_api.domain.entity;

import com.nostrapizza.estoque_api.domain.enums.MovementType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class StockMovement {

    private final UUID id;
    private final LocalDateTime createdAt;
    @Setter private Product product;
    @Setter private User user;
    @Setter private float quantity;
    @Setter private String note;
    @Setter private MovementType type;

    public StockMovement() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public StockMovement(UUID id, LocalDateTime createdAt, Product product,
                         User user, float quantity, String note, MovementType type) {
        this.id = id;
        this.createdAt = createdAt;
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.note = note;
        this.type = type;
    }
}
