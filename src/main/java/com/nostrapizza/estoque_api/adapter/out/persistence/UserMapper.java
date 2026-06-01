package com.nostrapizza.estoque_api.adapter.out.persistence;

import com.nostrapizza.estoque_api.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserJpaEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getEmail(), entity.getPasswordHash(),
                entity.getRole(), entity.isActive(), entity.getCreatedAt());
    }

    public UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity userJpaEntity = new UserJpaEntity();
        userJpaEntity.setId(user.getId());
        userJpaEntity.setName(user.getName());
        userJpaEntity.setEmail(user.getEmail());
        userJpaEntity.setPasswordHash(user.getPasswordHash());
        userJpaEntity.setRole(user.getRole());
        userJpaEntity.setActive(user.isActive());
        userJpaEntity.setCreatedAt(user.getCreatedAt());
        return userJpaEntity;
    }
}