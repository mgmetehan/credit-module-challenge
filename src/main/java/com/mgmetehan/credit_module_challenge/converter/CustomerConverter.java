package com.mgmetehan.credit_module_challenge.converter;

import com.mgmetehan.credit_module_challenge.dto.request.CreateCustomerDTO;
import com.mgmetehan.credit_module_challenge.dto.request.CreateLoanRequest;
import com.mgmetehan.credit_module_challenge.dto.request.UpdateCustomerRequest;
import com.mgmetehan.credit_module_challenge.dto.response.CustomerResponseDTO;
import com.mgmetehan.credit_module_challenge.model.Customer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerConverter {

    public static Customer toEntity(CreateCustomerDTO dto) {
        return Customer.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .creditLimit(dto.getCreditLimit())
                .build();
    }

    public static CustomerResponseDTO toResponseDTO(Customer customer) {
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .creditLimit(customer.getCreditLimit())
                .usedCreditLimit(customer.getUsedCreditLimit())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    public static UpdateCustomerRequest getUpdateCustomerRequest(CreateLoanRequest request, Customer customer) {
        return UpdateCustomerRequest.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .creditLimit(customer.getCreditLimit())
                .usedCreditLimit(customer.getUsedCreditLimit().add(request.getLoanAmount()))
                .build();
    }
}

