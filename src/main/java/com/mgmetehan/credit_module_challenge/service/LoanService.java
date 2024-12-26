package com.mgmetehan.credit_module_challenge.service;

import com.mgmetehan.credit_module_challenge.constant.LoanConstants;
import com.mgmetehan.credit_module_challenge.converter.CreateLoanConverter;
import com.mgmetehan.credit_module_challenge.converter.CustomerConverter;
import com.mgmetehan.credit_module_challenge.dto.request.CreateLoanRequest;
import com.mgmetehan.credit_module_challenge.dto.request.UpdateCustomerRequest;
import com.mgmetehan.credit_module_challenge.dto.response.CreateLoanResponse;
import com.mgmetehan.credit_module_challenge.model.Customer;
import com.mgmetehan.credit_module_challenge.model.Installment;
import com.mgmetehan.credit_module_challenge.model.Loan;
import com.mgmetehan.credit_module_challenge.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final CreateLoanConverter createLoanConverter;

    @Transactional
    public CreateLoanResponse createLoan(CreateLoanRequest request) {
        Customer customer = customerService.customerFindById(request.getCustomerId());

        if (customer.getCreditLimit().compareTo(request.getLoanAmount()) < 0) {
            throw new RuntimeException("Insufficient credit limit!");
        }

        double interestRate = calculateInterestRate(request.getNumberOfInstallment());

        BigDecimal totalAmount = request.getLoanAmount()
                .multiply(BigDecimal.valueOf(1 + interestRate))
                .setScale(2, RoundingMode.HALF_UP);

        Loan loan = createLoanConverter.toEntity(request, customer);
        loan.setLoanAmount(totalAmount);

        List<Installment> installments = getInstallments(request, loan, totalAmount);

        loan.setInstallments(installments);
        loan = loanRepository.save(loan);

        UpdateCustomerRequest updateCustomerRequest = CustomerConverter
                .getUpdateCustomerRequest(request, customer);

        customerService.updateCustomer(updateCustomerRequest);

        return createLoanConverter.toResponse(loan);
    }

    private double calculateInterestRate(String numberOfInstallment) {
        int months = Integer.parseInt(numberOfInstallment);

        return LoanConstants.INTEREST_RATES.entrySet()
                .stream()
                .filter(entry -> months <= entry.getKey())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid installment period. Maximum period is 24 months."));
    }

    private static List<Installment> getInstallments(CreateLoanRequest request, Loan loan, BigDecimal totalAmount) {
        BigDecimal installmentAmount = calculateInstallmentAmount(totalAmount, request.getNumberOfInstallment());

        LocalDate firstDueDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);

        return IntStream.range(0, Integer.valueOf(request.getNumberOfInstallment()))
                .mapToObj(i -> createInstallment(installmentAmount, firstDueDate.plusMonths(i), loan))
                .collect(Collectors.toList());
    }

    private static BigDecimal calculateInstallmentAmount(BigDecimal totalAmount, String numberOfInstallment) {
        return totalAmount.divide(BigDecimal.valueOf(Integer.valueOf(numberOfInstallment)),
                2, RoundingMode.HALF_UP);
    }

    private static Installment createInstallment(BigDecimal amount, LocalDate dueDate, Loan loan) {
        return Installment.builder()
                .amount(amount)
                .dueDate(dueDate)
                .loan(loan)
                .build();
    }
}