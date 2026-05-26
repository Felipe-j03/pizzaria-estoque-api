package com.nostrapizza.estoque_api.application.service;

import com.nostrapizza.estoque_api.application.port.in.ConsumptionReportItem;
import com.nostrapizza.estoque_api.application.port.in.GenerateConsumptionReportCommand;
import com.nostrapizza.estoque_api.application.port.in.GenerateConsumptionReportUseCase;
import com.nostrapizza.estoque_api.application.port.out.ProductRepository;
import com.nostrapizza.estoque_api.application.port.out.StockMovementRepository;
import com.nostrapizza.estoque_api.domain.entity.Product;
import com.nostrapizza.estoque_api.domain.entity.StockMovement;
import com.nostrapizza.estoque_api.domain.enums.MovementType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GenerateConsumptionReportService implements GenerateConsumptionReportUseCase {
    private final StockMovementRepository stockMovementRepository;

    @Override
    public List<ConsumptionReportItem> execute(GenerateConsumptionReportCommand command) {
        List<StockMovement> stockMovements = stockMovementRepository.findByPeriod(command.start(), command.end());

        long weeks = ChronoUnit.WEEKS.between(command.start(), command.end());
        if (weeks == 0) {
            weeks = 1;
        }
        long finalWeeks = weeks;

        return stockMovements.stream()
                .filter(movement -> movement.getType() == MovementType.OUT)
                .collect(Collectors.groupingBy(
                        StockMovement::getProduct))
                .entrySet().stream()
                .map(entry -> {
                    Product product = entry.getKey();
                    List<StockMovement> movements = entry.getValue();
                    float totalConsumption = (float) movements.stream()
                            .mapToDouble(StockMovement::getQuantity).sum();
                    float weeklyAverage = totalConsumption / finalWeeks;
                    return new ConsumptionReportItem(product.getName(), totalConsumption, weeklyAverage);
                })
                .toList();
    }
}