package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.DeactivateUserUseCase;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.domain.entity.User;
import com.nostrapizza.estoque_api.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeactivateUserService implements DeactivateUserUseCase {

    private final UserRepository userRepository;

    @Override
    public void execute(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        user.setActive(false);
        userRepository.save(user);
    }
}
