package com.service.bank.ebankservice.services;

import com.service.bank.ebankservice.dto.BankAccountRequestDTO;
import com.service.bank.ebankservice.dto.BankAccountResponseDTO;
import java.util.List;

public interface BankAccountService {
    BankAccountResponseDTO saveAccount(BankAccountRequestDTO requestDTO);
    BankAccountResponseDTO getAccount(String id);
    List<BankAccountResponseDTO> listAccounts();
    BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO requestDTO);
    void deleteAccount(String id);
}