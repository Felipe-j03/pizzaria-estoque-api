package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.enums.UserRole;

public record CreateUserCommand(
        String name,
        String email,
        String password,
        UserRole role
) {}
