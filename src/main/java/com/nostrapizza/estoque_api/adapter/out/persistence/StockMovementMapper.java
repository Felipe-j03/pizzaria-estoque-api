package com.nostrapizza.estoque_api.adapter.out.persistence;

import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.entity.StockMovement;
import com.nostrapizza.estoque_api.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockMovementMapper {

    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    public StockMovement toDomain(StockMovementJpaEntity entity) {
        Product product = productMapper.toDomain(entity.getProduct());
        User user = userMapper.toDomain(entity.getUser());
        return new StockMovement(entity.getId(), entity.getCreatedAt(), product, user,
                entity.getQuantity(), entity.getNote(), entity.getType());
    }

    public StockMovementJpaEntity toJpaEntity(StockMovement movement) {
        ProductJpaEntity productJpaEntity = productMapper.toJpaEntity(movement.getProduct());
        UserJpaEntity userJpaEntity = userMapper.toJpaEntity(movement.getUser());
        StockMovementJpaEntity stockMovementJpaEntity = new StockMovementJpaEntity();
        stockMovementJpaEntity.setId(movement.getId());
        stockMovementJpaEntity.setCreatedAt(movement.getCreatedAt());
        stockMovementJpaEntity.setProduct(productJpaEntity);
        stockMovementJpaEntity.setUser(userJpaEntity);
        stockMovementJpaEntity.setQuantity(movement.getQuantity());
        stockMovementJpaEntity.setNote(movement.getNote());
        stockMovementJpaEntity.setType(movement.getType());
        return stockMovementJpaEntity;
    }
}