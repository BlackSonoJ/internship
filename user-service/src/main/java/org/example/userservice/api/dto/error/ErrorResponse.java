package org.example.userservice.api.dto.error;


import java.time.Instant;

public record ErrorResponse (
        int statusCode,
        String message,
        Instant timestamp
){
}
