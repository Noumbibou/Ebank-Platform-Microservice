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

        try {
            Customer customer = customerRestClient.getCustomerById(requestDTO.getCustomerId());
            BankAccount bankAccount = bankAccountMapper.toBankAccount(requestDTO);

            // 3. Sauvegarde en base de données
            BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

            // 4. Transformation en DTO de réponse et association du client
            BankAccountResponseDTO responseDTO = bankAccountMapper.fromBankAccount(savedBankAccount);
            responseDTO.setCustomer(customer);
            return responseDTO;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
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
        // 1. Vérifier si le compte existe
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte bancaire introuvable"));

        // 2. Mettre à jour uniquement les champs autorisés (sans toucher au customerId)
        if (requestDTO.getBalance() != null) {
            bankAccount.setBalance(requestDTO.getBalance());
        }
        if (requestDTO.getCurrency() != null) {
            bankAccount.setCurrency(requestDTO.getCurrency());
        }
        if (requestDTO.getType() != null) {
            bankAccount.setType(requestDTO.getType());
        }

        // 3. Sauvegarde des modifications en base de données
        BankAccount updatedAccount = bankAccountRepository.save(bankAccount);

        // 4. Récupérer le client via OpenFeign pour l'inclure proprement dans le DTO de réponse
        Customer customer = customerRestClient.getCustomerById(updatedAccount.getCustomerId());

        BankAccountResponseDTO responseDTO = bankAccountMapper.fromBankAccount(updatedAccount);
        responseDTO.setCustomer(customer);

        return responseDTO;
    }

    @Override
    public void deleteAccount(String id) {
        bankAccountRepository.deleteById(id);
    }
}