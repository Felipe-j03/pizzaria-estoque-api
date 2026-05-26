package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.CreateUserCommand;
import com.nostrapizza.estoque_api.application.port.in.CreateUserUseCase;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.domain.entity.User;
import com.nostrapizza.estoque_api.domain.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User execute(CreateUserCommand command) {

        if(userRepository.existsByEmail(command.email())){
            throw new UserAlreadyExistsException("User with email " + command.email() + " already exists");
        }

        User user = new User();
        String hash = passwordEncoder.encode(command.password());
        user.setName(command.name());
        user.setEmail(command.email());
        user.setActive(true);
        user.setRole(command.role());
        user.setPasswordHash(hash);

        return userRepository.save(user);
    }
}
