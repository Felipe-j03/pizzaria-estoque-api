package com.nostrapizza.estoque_api.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface StockMovementJpaRepository extends JpaRepository<StockMovementJpaEntity, UUID> {
    List<StockMovementJpaEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
