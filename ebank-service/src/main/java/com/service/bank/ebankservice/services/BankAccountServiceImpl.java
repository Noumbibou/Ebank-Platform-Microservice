package com.service.bank.ebankservice.services;

import com.service.bank.ebankservice.dto.BankAccountRequestDTO;
import com.service.bank.ebankservice.dto.BankAccountResponseDTO;
import com.service.bank.ebankservice.entities.BankAccount;
import com.service.bank.ebankservice.feign.CustomerRestClient;
import com.service.bank.ebankservice.mapper.BankAccountMapper;
import com.service.bank.ebankservice.model.Customer;
import com.service.bank.ebankservice.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final CustomerRestClient customerRestClient;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper, CustomerRestClient customerRestClient) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
        this.customerRestClient = customerRestClient;
    }

    @Override
    public BankAccountResponseDTO saveAccount(BankAccountRequestDTO requestDTO) {
        // 1. Utilisation du mapper pour transformer le DTO en Entité
        BankAccount bankAccount = bankAccountMapper.toBankAccount(requestDTO);

        // 3. Sauvegarde en base de données
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        // 4. Retour du DTO de réponse
        return bankAccountMapper.fromBankAccount(savedBankAccount);
    }

    @Override
    public BankAccountResponseDTO getAccount(String id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte bancaire introuvable"));

        Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());

        BankAccountResponseDTO responseDTO = bankAccountMapper.fromBankAccount(bankAccount);

        // 3. Association du client récupéré au DTO de réponse
        responseDTO.setCustomer(customer);

        return responseDTO;
    }

    @Override
    public List<BankAccountResponseDTO> listAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(bankAccountMapper::fromBankAccount)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO requestDTO) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte bancaire introuvable"));

        if (requestDTO.getBalance() != null) bankAccount.setBalance(requestDTO.getBalance());
        if (requestDTO.getCurrency() != null) bankAccount.setCurrency(requestDTO.getCurrency());
        if (requestDTO.getType() != null) bankAccount.setType(requestDTO.getType());

        BankAccount updatedAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.fromBankAccount(updatedAccount);
    }

    @Override
    public void deleteAccount(String id) {
        bankAccountRepository.deleteById(id);
    }
}