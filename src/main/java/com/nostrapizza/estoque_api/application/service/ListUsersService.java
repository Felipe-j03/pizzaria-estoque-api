package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.ListUsersUseCase;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUsersService implements ListUsersUseCase {

    private final UserRepository userRepository;

    @Override
    public List<User> execute() {
        return userRepository.findAll();
    }

}
