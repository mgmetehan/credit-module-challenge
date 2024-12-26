package com.mgmetehan.credit_module_challenge.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotNull(message = "Number of installments is required")
    @Pattern(regexp = "^(6|9|12|24)$", message = "Number of installments must be either 6, 9, 12 or 24")
    private String numberOfInstallment;
}