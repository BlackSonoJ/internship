package org.example.notificationservice.api.dto;

import jakarta.validation.constraints.Email;
import org.example.notificationservice.common.enums.Operation;

public record MessageDto(
        Operation operation,
        @Email String email
) {
}
