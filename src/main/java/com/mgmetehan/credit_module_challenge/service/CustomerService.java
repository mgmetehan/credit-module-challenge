package com.mgmetehan.credit_module_challenge.service;

import com.mgmetehan.credit_module_challenge.converter.CustomerConverter;
import com.mgmetehan.credit_module_challenge.dto.request.CreateCustomerDTO;
import com.mgmetehan.credit_module_challenge.dto.response.CustomerResponseDTO;
import com.mgmetehan.credit_module_challenge.model.Customer;
import com.mgmetehan.credit_module_challenge.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponseDTO createCustomer(CreateCustomerDTO requestDTO) {
        Customer customer = CustomerConverter.toEntity(requestDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerConverter.toResponseDTO(savedCustomer);
    }
}
