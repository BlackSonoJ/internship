package org.example.userservice.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PostOrPutUserDto (
        @NotBlank String name,
        @Min(0) @Max(120) short age,
        @Email String email
){
}
