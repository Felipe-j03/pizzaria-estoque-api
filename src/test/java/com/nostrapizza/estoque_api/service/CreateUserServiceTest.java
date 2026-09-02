package com.nostrapizza.estoque_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nostrapizza.estoque_api.application.port.in.CreateProductCommand;
import com.nostrapizza.estoque_api.application.port.in.CreateUserCommand;
import com.nostrapizza.estoque_api.application.port.out.UserRepository;
import com.nostrapizza.estoque_api.application.service.CreateUserService;
import com.nostrapizza.estoque_api.domain.entity.User;
import com.nostrapizza.estoque_api.domain.enums.UserRole;
import com.nostrapizza.estoque_api.domain.exception.ProductAlreadyExistsException;
import com.nostrapizza.estoque_api.domain.exception.UserAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class CreateUserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    CreateUserService createUserService;

    @Test
    void shouldSucceedWhenEmailDontExist() {

        UUID userId = UUID.randomUUID();
        User user = new User(userId, "veio-do-banco", "banco@email.com", null, null, true, LocalDateTime.now());

        CreateUserCommand command = new CreateUserCommand("Felipe", "Felipe@email.com", "hash",
                UserRole.MANAGER);

        when(userRepository.existsByEmail(command.email())).thenReturn(false);
        when(passwordEncoder.encode(command.password())).thenReturn("hashed-password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userResult = createUserService.execute(command);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User userSaved = captor.getValue();

        assertSame(user, userResult);
        assertEquals(command.name(), userSaved.getName());
        assertEquals(command.email(), userSaved.getEmail());
        assertEquals(command.role(), userSaved.getRole());
        assertEquals("hashed-password", userSaved.getPasswordHash());
        assertTrue(userSaved.isActive());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {

        CreateUserCommand command = new CreateUserCommand("Felipe", "Felipe@email.com", "has", UserRole.MANAGER);

        when(userRepository.existsByEmail(command.email())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            createUserService.execute(command);
        });

        verify(userRepository, never()).save(any());
    }

}
