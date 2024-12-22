package com.mgmetehan.credit_module_challenge.controller;

import com.mgmetehan.credit_module_challenge.dto.request.CreateCustomerDTO;
import com.mgmetehan.credit_module_challenge.dto.response.CustomerResponseDTO;
import com.mgmetehan.credit_module_challenge.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CreateCustomerDTO requestDTO) {
        CustomerResponseDTO response = customerService.createCustomer(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
