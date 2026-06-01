package com.nostrapizza.estoque_api.adapter.out.persistence;

import com.nostrapizza.estoque_api.application.port.out.StockMovementRepository;
import com.nostrapizza.estoque_api.domain.entity.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StockMovementRepositoryImpl implements StockMovementRepository {

    private final StockMovementJpaRepository jpaRepository;
    private final StockMovementMapper mapper;

    @Override
    public StockMovement save(StockMovement stockMovement) {
        StockMovementJpaEntity jpaEntity = mapper.toJpaEntity(stockMovement);
        StockMovementJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<StockMovement> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<StockMovement> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<StockMovement> findByPeriod(LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByCreatedAtBetween(start, end).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
