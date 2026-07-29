package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.UpdateUserCommand;
import com.nostrapizza.estoque_api.application.port.in.UpdateUserUseCase;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.domain.entity.User;
import com.nostrapizza.estoque_api.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepository userRepository;

    @Override
    public User execute(UpdateUserCommand command) {

        User user = userRepository.findById(command.id()).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        if (command.name() != null) {
            user.setName(command.name());
        }
        if (command.role() != null) {
            user.setRole(command.role());
        }

        return userRepository.save(user);
    }
}
