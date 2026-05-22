package com.nostrapizza.estoque_api.application.port.in;

public interface LoginUseCase {
    LoginResult execute(LoginCommand loginCommand);
}
