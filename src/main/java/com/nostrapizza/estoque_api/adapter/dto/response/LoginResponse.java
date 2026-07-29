package com.nostrapizza.estoque_api.adapter.dto.response;

import com.nostrapizza.estoque_api.domain.enums.UserRole;

import java.util.UUID;

public record LoginResponse(
        String token,
        UUID userId,
        String name,
        UserRole role
) {
}