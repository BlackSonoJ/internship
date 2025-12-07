package org.example.userservice.api.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record UserDto (
        Long id,
        @NotBlank String name,
        @Min(0) @Max(120) short age,
        @Email String email,
        @JsonProperty("created_at") Instant createdAt
) {
}
