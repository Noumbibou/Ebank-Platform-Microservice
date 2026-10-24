package com.service.bank.customerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

public record CustomerResponseDTO(

        Long id,

        @NotBlank(message = "ce champ est obligatoire")
        String name,

        @Email(message = "format d email invalide")
        @NotBlank(message = "l email est obligatoire")
        String email,

        Long phone,

        Date createAt
) {
}
