package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.enums.UserRole;

import java.util.UUID;

public record LoginResult(
        String token,
        UUID userId,
        String name,
        UserRole role
) {
}
