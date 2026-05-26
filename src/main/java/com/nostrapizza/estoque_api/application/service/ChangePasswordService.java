package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.ChangePasswordCommand;
import com.nostrapizza.estoque_api.application.port.in.ChangePasswordUseCase;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.domain.entity.User;
import com.nostrapizza.estoque_api.domain.exception.InvalidCredentialsException;
import com.nostrapizza.estoque_api.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordService implements ChangePasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void execute(ChangePasswordCommand command) {

        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + command.userId()));
        if (!passwordEncoder.matches(command.currentPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        user.setPasswordHash(passwordEncoder.encode(command.newPassword()));
        userRepository.save(user);
    }
}
