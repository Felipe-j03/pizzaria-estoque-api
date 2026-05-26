package com.nostrapizza.estoque_api.adapter.out.persistence;

import com.nostrapizza.estoque_api.domain.enums.MovementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "stock_movements")
public class StockMovementJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpaEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @Column(nullable = false)
    private float quantity;

    @Column
    private String note;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Column(name = "created_at", nullable = false,  updatable = false)
    private LocalDateTime createdAt;
}
