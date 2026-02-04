package org.example.userservice.infrastructure.kafka.events;

import jakarta.validation.constraints.Email;
import org.example.userservice.common.enums.Operation;

public record MessageDto (
        Operation operation,
        @Email String email
) {
}
