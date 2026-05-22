package com.nostrapizza.estoque_api.domain.entity;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Product {

    private final UUID id;
    private final LocalDateTime createdAt;
    @Setter private String name;
    @Setter private String unit;
    @Setter private float currentQuantity;
    @Setter private float minQuantity;
    @Setter private boolean active;

    public Product() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

}
