package com.mgmetehan.credit_module_challenge.service;

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
import java.util.ArrayList;
import java.util.List;

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
            throw new RuntimeException("Kredi limiti yetersiz!");
        }

        Loan loan = createLoanConverter.toEntity(request, customer);

        List<Installment> installments = new ArrayList<>();
        BigDecimal installmentAmount = request.getLoanAmount()
                .divide(BigDecimal.valueOf(request.getNumberOfInstallment()), 2, RoundingMode.HALF_UP);

        LocalDate dueDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        for (int i = 0; i < request.getNumberOfInstallment(); i++) {
            Installment installment = Installment.builder()
                    .amount(installmentAmount)
                    .paidAmount(BigDecimal.ZERO)
                    .dueDate(dueDate)
                    .isPaid(false)
                    .loan(loan)
                    .build();
            installments.add(installment);
            dueDate = dueDate.plusMonths(1);
        }

        loan.setInstallments(installments);
        loan = loanRepository.save(loan);

        UpdateCustomerRequest updateCustomerRequest = CustomerConverter
                .getUpdateCustomerRequest(request, customer);

        customerService.updateCustomer(updateCustomerRequest);

        return createLoanConverter.toResponse(loan);
    }
}