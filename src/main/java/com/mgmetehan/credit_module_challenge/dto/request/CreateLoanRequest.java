package com.mgmetehan.credit_module_challenge.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanRequest {
    private Long customerId;
    private BigDecimal loanAmount;
    private Integer numberOfInstallment;
}