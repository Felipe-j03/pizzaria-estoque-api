    package com.nostrapizza.estoque_api.adapter.in.web;
    
    import com.nostrapizza.estoque_api.adapter.dto.request.LoginRequest;
    import com.nostrapizza.estoque_api.adapter.dto.response.LoginResponse;
    import com.nostrapizza.estoque_api.application.port.in.LoginCommand;
    import com.nostrapizza.estoque_api.application.port.in.LoginResult;
    import com.nostrapizza.estoque_api.application.port.in.LoginUseCase;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    
    @RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
    public class AuthController {
    
        private final LoginUseCase loginUseCase;
    
        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
            LoginCommand command = new LoginCommand(request.email(), request.password());
            LoginResult result = loginUseCase.execute(command);
    
            LoginResponse response = new LoginResponse(
                    result.token(),
                    result.userId(),
                    result.name(),
                    result.role()
            );
    
            return ResponseEntity.ok(response);
        }
    }