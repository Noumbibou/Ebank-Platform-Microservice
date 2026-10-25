package com.service.bank.ebankservice;

import com.service.bank.ebankservice.dto.BankAccountRequestDTO;
import com.service.bank.ebankservice.entities.AccountType;
import com.service.bank.ebankservice.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EbankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            // Pour 3 clients différents (ID de 1 à 3)
            for (long i = 1; i <= 3; i++) {
                // On crée 5 comptes pour chaque client
                for (int j = 0; j < 5; j++) {
                    String currency = (j % 2 == 0) ? "MAD" : "USD";
                    AccountType type = (j < 3) ? AccountType.CURRENT_ACCOUNT : AccountType.SAVINGS_ACCOUNT;
                    double balance = 1000 + Math.random() * 90000;

                    BankAccountRequestDTO requestDTO = BankAccountRequestDTO.builder()
                            .balance(balance)
                            .currency(currency)
                            .type(type)
                            .customerId(i) // Associe le compte au client i
                            .build();

                    bankAccountService.saveAccount(requestDTO);
                }
            }
            System.out.println("Données de test (Comptes bancaires) insérées avec succès !");
        };
    }
}
