package com.nostrapizza.estoque_api.application.port.out;

import com.nostrapizza.estoque_api.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    boolean existsByEmail(String email);
}