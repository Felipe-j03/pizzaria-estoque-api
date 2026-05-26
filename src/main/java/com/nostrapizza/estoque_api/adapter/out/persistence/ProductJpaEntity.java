package com.nostrapizza.estoque_api.adapter.out.persistence;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String unit;

    @Column(name = "current_quantity", nullable = false)
    private float currentQuantity;

    @Column(name = "min_quantity", nullable = false)
    private float minQuantity;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
