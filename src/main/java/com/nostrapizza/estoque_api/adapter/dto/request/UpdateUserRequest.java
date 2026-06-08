package com.nostrapizza.estoque_api.adapter.dto.request;

import com.nostrapizza.estoque_api.domain.enums.UserRole;

public record UpdateUserRequest(
        String name,
        UserRole role
) {
}
