package com.service.bank.ebankservice.web;

import com.service.bank.ebankservice.dto.BankAccountRequestDTO;
import com.service.bank.ebankservice.dto.BankAccountResponseDTO;
import com.service.bank.ebankservice.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankAccountRestController {

    private final BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccountResponseDTO> bankAccounts() {
        return bankAccountService.listAccounts();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccountResponseDTO getBankAccount(@PathVariable String id) {
        return bankAccountService.getAccount(id);
    }

    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO requestDTO) {
        return bankAccountService.saveAccount(requestDTO);
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccountResponseDTO update(@PathVariable String id, @RequestBody BankAccountRequestDTO requestDTO) {
        return bankAccountService.updateAccount(id, requestDTO);
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void deleteBankAccount(@PathVariable String id) {
        bankAccountService.deleteAccount(id);
    }
}