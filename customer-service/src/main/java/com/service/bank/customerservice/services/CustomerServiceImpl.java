package com.service.bank.customerservice.services;

import com.service.bank.customerservice.dto.CustomerRequestDTO;
import com.service.bank.customerservice.dto.CustomerResponseDTO;
import com.service.bank.customerservice.entities.Customer;
import com.service.bank.customerservice.mapper.CustomerMapper;
import com.service.bank.customerservice.repositories.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @McpTool(description = "save a new customer")
    public CustomerResponseDTO addCustomer(@McpToolParam(description = "The customer to save (name, email, phone)") CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerMapper.customerRequestDtoToCustomer(customerRequestDTO);

        Customer customerSave = customerRepository.save(customer);


        return customerMapper.customerToCustomerReponseDto(customerSave);
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerRequestDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customerMapper.updateCustomerFromDto(customerRequestDTO, existingCustomer);

        Customer savedCustomer = customerRepository.save(existingCustomer);

        return customerMapper.customerToCustomerReponseDto(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    @McpTool(description = "Get All customers")
    public List<CustomerResponseDTO> customerList() {
//        List<Customer> customerList = customerRepository.findAll();
//        List<CustomerResponseDTO> customerResponseDTOList = new java.util.ArrayList<>(List.of());
//        customerList.forEach(c -> {
//            customerResponseDTOList.add(customerMapper.customerToCustomerReponseDto(c));
//        });
//        return customerResponseDTOList;
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerReponseDto)
                .toList();
    }

    @Override
    @McpTool(description = "find a customer by id")
    public CustomerResponseDTO customer(@McpToolParam(description = "The customer id") Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.customerToCustomerReponseDto(customer);
    }
}
