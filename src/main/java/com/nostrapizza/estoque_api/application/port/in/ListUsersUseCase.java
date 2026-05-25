package com.nostrapizza.estoque_api.application.port.in;

import com.nostrapizza.estoque_api.domain.entity.User;

import java.util.List;

public interface ListUsersUseCase {
    List<User> execute();
}
