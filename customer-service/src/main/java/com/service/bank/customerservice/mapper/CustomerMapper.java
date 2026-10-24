package com.service.bank.customerservice.mapper;

import com.service.bank.customerservice.dto.CustomerRequestDTO;
import com.service.bank.customerservice.dto.CustomerResponseDTO;
import com.service.bank.customerservice.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    public Customer customerRequestDtoToCustomer(CustomerRequestDTO customerRequestDTO);
    public CustomerResponseDTO customerToCustomerReponseDto(Customer customer);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    void updateCustomerFromDto(CustomerRequestDTO dto, @MappingTarget Customer customer);
}
