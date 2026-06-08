package com.nostrapizza.estoque_api.adapter.dto.response;

import com.nostrapizza.estoque_api.domain.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        UserRole role,
        boolean active,
        LocalDateTime createdAt
) {
}
