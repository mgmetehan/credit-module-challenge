package com.mgmetehan.credit_module_challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLoanDTO {
    private Long id;
    private BigDecimal loanAmount;
    private Integer numberOfInstallment;
    private Boolean isPaid;
    private Long customerId;
} 