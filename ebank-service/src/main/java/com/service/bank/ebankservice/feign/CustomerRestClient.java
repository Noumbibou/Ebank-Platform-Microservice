package com.service.bank.ebankservice.feign;

import com.service.bank.ebankservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerRestClient {

    @GetMapping("api/customers/{id}")
    Customer getCustomerById(@PathVariable Long id);
}
