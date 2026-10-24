package com.service.bank.customerservice.services;

import com.service.bank.customerservice.dto.CustomerRequestDTO;
import com.service.bank.customerservice.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO);
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerRequestDTO);
    public void deleteCustomer(Long id);
    public List<CustomerResponseDTO> customerList();
    public CustomerResponseDTO customer(Long id);

}
