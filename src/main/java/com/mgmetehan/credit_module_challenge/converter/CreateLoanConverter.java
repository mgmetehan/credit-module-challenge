package com.mgmetehan.credit_module_challenge.converter;

import com.mgmetehan.credit_module_challenge.dto.request.CreateLoanRequest;
import com.mgmetehan.credit_module_challenge.dto.response.CreateLoanResponse;
import com.mgmetehan.credit_module_challenge.model.Customer;
import com.mgmetehan.credit_module_challenge.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class CreateLoanConverter {

    public Loan toEntity(CreateLoanRequest request, Customer customer) {
        return Loan.builder()
                .loanAmount(request.getLoanAmount())
                .numberOfInstallment(request.getNumberOfInstallment())
                .isPaid(false)
                .customer(customer)
                .build();
    }

    public CreateLoanResponse toResponse(Loan loan) {
        return CreateLoanResponse.builder()
                .id(loan.getId())
                .customerId(loan.getCustomer().getId())
                .loanAmount(loan.getLoanAmount())
                .numberOfInstallment(loan.getNumberOfInstallment())
                .createDate(loan.getCreatedAt())
                .isPaid(loan.getIsPaid())
                .build();
    }
} 