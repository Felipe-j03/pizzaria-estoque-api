package com.nostrapizza.estoque_api.application.port.in;

import java.util.UUID;

public interface DeactivateProductUseCase {
    void execute(UUID productId);
}
