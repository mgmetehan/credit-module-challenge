package com.mgmetehan.credit_module_challenge.controller;

import com.mgmetehan.credit_module_challenge.dto.request.CreateCustomerDTO;
import com.mgmetehan.credit_module_challenge.dto.request.UpdateCreditLimitDTO;
import com.mgmetehan.credit_module_challenge.dto.response.CustomerResponseDTO;
import com.mgmetehan.credit_module_challenge.dto.response.ResponseLoanDTO;
import com.mgmetehan.credit_module_challenge.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        CustomerResponseDTO response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/credit-limit")
    public ResponseEntity<CustomerResponseDTO> updateCreditLimit(@PathVariable Long id,@Valid @RequestBody UpdateCreditLimitDTO requestDTO) {
        CustomerResponseDTO response = customerService.updateCreditLimit(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> response = customerService.getAllCustomers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}/loans")
    public ResponseEntity<List<ResponseLoanDTO>> getCustomerLoans(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerLoans(customerId));
    }
}
