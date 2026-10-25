package com.service.bank.customerservice;

import com.service.bank.customerservice.entities.Customer;
import com.service.bank.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository) {
        return args -> {
            for (int i = 0; i < 10; i++) {
                Customer customer = Customer.builder()
                        .name("william " +(i+1))
                        .createAt(new Date())
                        .email("email" + (i+1) + ("@gmail.com"))
                        .phone(ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L) )
                        .build();
                customerRepository.save(customer);
            };
        };
    }
}
