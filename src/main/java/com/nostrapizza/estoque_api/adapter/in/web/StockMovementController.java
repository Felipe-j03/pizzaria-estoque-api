package com.nostrapizza.estoque_api.adapter.in.web;

import com.nostrapizza.estoque_api.adapter.dto.request.RegisterStockMovementRequest;
import com.nostrapizza.estoque_api.adapter.dto.response.StockMovementResponse;
import com.nostrapizza.estoque_api.application.port.in.*;
import com.nostrapizza.estoque_api.domain.entity.StockMovement;
import com.nostrapizza.estoque_api.domain.enums.MovementType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final RegisterStockMovementUseCase registerStockMovementUseCase;
    private final ListStockMovementsUseCase listStockMovementsUseCase;
    private final GenerateConsumptionReportUseCase generateConsumptionReportUseCase;

    @PostMapping
    public ResponseEntity<StockMovementResponse> create(
            @Valid @RequestBody RegisterStockMovementRequest request,
            @RequestHeader("X-User-Id") UUID userId) {

        RegisterStockMovementCommand command = new RegisterStockMovementCommand(request.productId(), userId,
                request.quantity(), request.type(), request.note());
        StockMovement stockMovement = registerStockMovementUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(stockMovement));
    }

    @GetMapping
    public ResponseEntity<List<StockMovementResponse>> findAll(
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) MovementType type,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end) {

        ListStockMovementsCommand command = new ListStockMovementsCommand(
                productId, userId, type, start, end);
        List<StockMovement> stockMovements = listStockMovementsUseCase.execute(command);

        List<StockMovementResponse> stockMovementResponses = stockMovements.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(stockMovementResponses);
    }

    @GetMapping("/report")
    public ResponseEntity<List<ConsumptionReportItem>> generateReport(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {

        GenerateConsumptionReportCommand command = new GenerateConsumptionReportCommand(start, end);
        List<ConsumptionReportItem> report = generateConsumptionReportUseCase.execute(command);
        return ResponseEntity.ok(report);
    }


    private StockMovementResponse toResponse(StockMovement movement) {
        return new StockMovementResponse(
                movement.getId(),
                movement.getProduct().getId(),
                movement.getUser().getId(),
                movement.getQuantity(),
                movement.getType(),
                movement.getNote(),
                movement.getCreatedAt()
        );
    }
}