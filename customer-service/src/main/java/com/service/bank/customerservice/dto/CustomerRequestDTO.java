package com.service.bank.customerservice.dto;

public record CustomerRequestDTO(
        String name,
        String email,
        Long phone
) {
}
