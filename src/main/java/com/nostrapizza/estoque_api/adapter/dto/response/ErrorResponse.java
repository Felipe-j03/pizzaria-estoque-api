package com.nostrapizza.estoque_api.adapter.dto.response;

import java.util.Map;

public record ErrorResponse(
        long timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fields) {
}
