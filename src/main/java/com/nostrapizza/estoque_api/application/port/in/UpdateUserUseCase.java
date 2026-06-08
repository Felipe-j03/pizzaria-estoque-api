package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.entity.User;

public interface UpdateUserUseCase {
    User execute(UpdateUserCommand product);
}
