package com.haru.marketspring.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record ApiErrorResponse(
        String message,
        String error,
        OffsetDateTime timestamp,
        List<String> details
) {
    public static ApiErrorResponse of(String message, String error, List<String> details) {
        return new ApiErrorResponse(
                message,
                error,
                OffsetDateTime.now(),
                details
        );
    }

    public static ApiErrorResponse of(String message, String error) {
        return of(message, error, List.of());
    }
}