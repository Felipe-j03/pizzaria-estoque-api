package com.nostrapizza.estoque_api.application.port.in;

import java.util.List;

public interface GenerateConsumptionReportUseCase {
    List<ConsumptionReportItem> execute(GenerateConsumptionReportCommand command);
}
