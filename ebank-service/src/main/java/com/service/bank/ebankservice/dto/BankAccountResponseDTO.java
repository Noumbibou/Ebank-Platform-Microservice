package com.service.bank.ebankservice.dto;

import com.service.bank.ebankservice.entities.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccountResponseDTO {
    private String id;
    private Double balance;
    private String currency;
    private AccountType type;
    private Long customerId;
}