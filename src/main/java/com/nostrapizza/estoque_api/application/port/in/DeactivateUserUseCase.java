package com.nostrapizza.estoque_api.application.port.in;

import java.util.UUID;

public interface DeactivateUserUseCase {
    void execute(UUID userId);
}
