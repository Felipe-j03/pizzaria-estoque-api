package com.nostrapizza.estoque_api.application.port.in;

public record ConsumptionReportItem(
        String productName,
        float totalConsumption,
        float weeklyAverage
) {
}
