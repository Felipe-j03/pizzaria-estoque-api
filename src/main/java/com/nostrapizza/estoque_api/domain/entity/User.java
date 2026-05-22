package com.nostrapizza.estoque_api.domain.entity;

import com.nostrapizza.estoque_api.domain.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class User {

    private final UUID id;
    private final LocalDateTime createdAt;
    @Setter private String name;
    @Setter private String email;
    @Setter private String passwordHash;
    @Setter private UserRole role;
    @Setter private boolean active;

    public User() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

}