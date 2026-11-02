package com.service.bank.customerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(
        @NotBlank(message = "le nom est obligatoire")
        String name,
        @Email
        @NotBlank(message = "l'email est obligatoire")
        String email,
        Long phone
) {
}
