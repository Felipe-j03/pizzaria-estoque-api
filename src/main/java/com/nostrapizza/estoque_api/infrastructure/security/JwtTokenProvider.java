package com.nostrapizza.estoque_api.infrastructure.security;

import com.nostrapizza.estoque_api.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String generate(User user) {
        // TODO: implementar geração do JWT
        return null;
    }
}