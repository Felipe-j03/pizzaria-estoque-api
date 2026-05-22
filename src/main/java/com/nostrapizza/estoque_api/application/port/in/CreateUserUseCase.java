package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.entity.User;

public interface CreateUserUseCase {
    User execute(CreateUserCommand command);
}
