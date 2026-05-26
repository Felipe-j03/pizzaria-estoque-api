package com.nostrapizza.estoque_api.application.port.in;

import java.time.LocalDateTime;

public record GenerateConsumptionReportCommand(
        LocalDateTime start,
        LocalDateTime end
) {
}
