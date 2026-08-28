package com.nostrapizza.estoque_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nostrapizza.estoque_api.application.port.in.LoginCommand;
import com.nostrapizza.estoque_api.application.port.in.LoginResult;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.application.service.LoginService;
import com.nostrapizza.estoque_api.domain.entity.User;
import com.nostrapizza.estoque_api.domain.enums.UserRole;
import com.nostrapizza.estoque_api.domain.exception.InvalidCredentialsException;
import com.nostrapizza.estoque_api.domain.exception.UserNotActiveException;
import com.nostrapizza.estoque_api.infrastructure.security.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private LoginService loginService;

    @Test
    void shouldReturnTokenWhenCorrect() {

        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Felipe", "Felipe@email.com", "hash", UserRole.MANAGER, true, LocalDateTime.now());

        LoginCommand command = new LoginCommand("Felipe@email.com", "hash");

        when(userRepository.findByEmail(command.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(command.password(), user.getPasswordHash())).thenReturn(true);
        when(jwtTokenProvider.generateToken(user)).thenReturn("fake-jwt-token");

        LoginResult loginResult = loginService.execute(command);

        assertNotNull(loginResult);
        assertEquals("fake-jwt-token", loginResult.token());
        assertEquals(userId, loginResult.userId());
        assertEquals("Felipe", loginResult.name());
        assertEquals(UserRole.MANAGER, loginResult.role());
    }

    @Test
    void shouldThrowWhenEmailNotFound() {

        LoginCommand command = new LoginCommand("Felipe@email.com", "hash");

        when(userRepository.findByEmail(command.email())).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> {
            loginService.execute(command);
        });

    }

    @Test
    void shouldThrowWhenUserNotActive() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Felipe", "Felipe@email.com", "hash", UserRole.MANAGER, false,
                LocalDateTime.now());

        LoginCommand command = new LoginCommand("Felipe@email.com", "hash");

        when(userRepository.findByEmail(command.email())).thenReturn(Optional.of(user));

        assertThrows(UserNotActiveException.class, () -> {
            loginService.execute(command);
        });

    }

    @Test
    void shouldThrowWhenPasswordIncorrect() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Felipe", "Felipe@email.com", "hash", UserRole.MANAGER, true,
                LocalDateTime.now());

        LoginCommand command = new LoginCommand("Felipe@email.com", "hash");

        when(userRepository.findByEmail(command.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(command.password(), user.getPasswordHash())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> {
            loginService.execute(command);
        });
    }
}
