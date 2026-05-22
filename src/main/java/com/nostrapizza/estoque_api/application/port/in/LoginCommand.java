package com.nostrapizza.estoque_api.application.port.in;

public record LoginCommand(
        String email,
        String password
) {
}
