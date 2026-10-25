package com.service.bank.ebankservice.mapper;

import com.service.bank.ebankservice.dto.BankAccountRequestDTO;
import com.service.bank.ebankservice.dto.BankAccountResponseDTO;
import com.service.bank.ebankservice.entities.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountResponseDTO fromBankAccount(BankAccount bankAccount);
    // 2. Convertir le DTO de requête vers l'Entité
    @Mapping(target = "id", ignore = true)        // L'ID sera généré (UUID) dans le service
    @Mapping(target = "createdAt", ignore = true) // La date sera générée (new Date()) dans le service
    @Mapping(target = "customer", ignore = true) // Ignoré car @Transient
    BankAccount toBankAccount(BankAccountRequestDTO requestDTO);
}