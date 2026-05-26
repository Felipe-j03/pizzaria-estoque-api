package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.LoginCommand;
import com.nostrapizza.estoque_api.application.port.in.LoginResult;
import com.nostrapizza.estoque_api.application.port.in.LoginUseCase;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.domain.entity.User;
import com.nostrapizza.estoque_api.domain.exception.InvalidCredentialsException;
import com.nostrapizza.estoque_api.domain.exception.UserNotActiveException;
import com.nostrapizza.estoque_api.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public LoginResult execute(LoginCommand command) {

        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!user.isActive()) {
            throw new UserNotActiveException("Account deactivated, contact your manager");
        }

        if (!passwordEncoder.matches(command.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtTokenProvider.generate(user);
        return new LoginResult(token, user.getId(), user.getName(), user.getRole());
    }
}
