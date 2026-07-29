package com.nostrapizza.estoque_api.adapter.in.web;

import com.nostrapizza.estoque_api.adapter.dto.request.RegisterUserRequest;
import com.nostrapizza.estoque_api.adapter.dto.request.UpdateUserRequest;
import com.nostrapizza.estoque_api.adapter.dto.response.UserResponse;
import com.nostrapizza.estoque_api.application.port.in.*;
import com.nostrapizza.estoque_api.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeactivateUserUseCase deactivateUserUseCase;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody RegisterUserRequest request) {

        CreateUserCommand command = new CreateUserCommand(request.name(), request.email(), request.password(), request.role());
        User user = createUserUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<UserResponse>> findAll() {

        List<User> users = listUsersUseCase.execute();
        List<UserResponse> userResponses = users.stream().map(this::toResponse).toList();

        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        User user = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(toResponse(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {

        UpdateUserCommand command = new UpdateUserCommand(id, request.name(), request.role());
        User user = updateUserUseCase.execute(command);
        return ResponseEntity.ok(toResponse(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deactivateUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }


    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt()
        );
    }
}