package com.mgmetehan.credit_module_challenge.converter;

import com.mgmetehan.credit_module_challenge.dto.response.ResponseLoanDTO;
import com.mgmetehan.credit_module_challenge.model.Loan;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoanConverter {
    
    public static ResponseLoanDTO toLoanResponse(Loan loan) {
        return ResponseLoanDTO.builder()
                .id(loan.getId())
                .loanAmount(loan.getLoanAmount())
                .numberOfInstallment(loan.getNumberOfInstallment())
                .isPaid(loan.getIsPaid())
                .customerId(loan.getCustomer().getId())
                .build();
    }
} 