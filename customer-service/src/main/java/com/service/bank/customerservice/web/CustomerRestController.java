package com.service.bank.customerservice.web;

import com.service.bank.customerservice.dto.CustomerRequestDTO;
import com.service.bank.customerservice.dto.CustomerResponseDTO;
import com.service.bank.customerservice.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

        private final CustomerService customerService;

        public CustomerRestController(CustomerService customerService) {
            this.customerService = customerService;
        }

        // Récupérer la liste de tous les clients (sous forme de DTO)
        @GetMapping
        public List<CustomerResponseDTO> getAllCustomers() {
            return customerService.customerList();
        }

        // Ajouter un client
        @PostMapping
        public CustomerResponseDTO saveCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
            return customerService.addCustomer(customerRequestDTO);
        }

        // Récupérer un client par son ID
        @GetMapping("/{id}")
        public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
            return customerService.customer(id);
        }

        @DeleteMapping("/{id}")
        public void deleteCustomerById(@PathVariable Long id){
            customerService.deleteCustomer(id);
        }
        @PutMapping("/{id}")
        public void updateCustomer(@PathVariable Long id, CustomerRequestDTO customerRequestDTO){
            customerService.updateCustomer(id, customerRequestDTO);
        }


}
