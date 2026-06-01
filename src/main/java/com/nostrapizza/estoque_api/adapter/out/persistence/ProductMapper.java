package com.nostrapizza.estoque_api.adapter.out.persistence;

import com.nostrapizza.estoque_api.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductJpaEntity entity) {
        return new Product(entity.getId(), entity.getCreatedAt(), entity.getName(), entity.getUnit(),
                entity.getCurrentQuantity(), entity.getMinQuantity(), entity.isActive());
    }

    public ProductJpaEntity toJpaEntity(Product product) {
        ProductJpaEntity productJpaEntity = new ProductJpaEntity();
        productJpaEntity.setId(product.getId());
        productJpaEntity.setCreatedAt(product.getCreatedAt());
        productJpaEntity.setName(product.getName());
        productJpaEntity.setUnit(product.getUnit());
        productJpaEntity.setCurrentQuantity(product.getCurrentQuantity());
        productJpaEntity.setMinQuantity(product.getMinQuantity());
        productJpaEntity.setActive(product.isActive());
        return productJpaEntity;
    }
}