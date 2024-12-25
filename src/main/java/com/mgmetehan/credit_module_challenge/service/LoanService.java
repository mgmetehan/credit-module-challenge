package com.mgmetehan.credit_module_challenge.service;

import com.mgmetehan.credit_module_challenge.converter.CreateLoanConverter;
import com.mgmetehan.credit_module_challenge.converter.CustomerConverter;
import com.mgmetehan.credit_module_challenge.dto.request.CreateLoanRequest;
import com.mgmetehan.credit_module_challenge.dto.request.UpdateCustomerRequest;
import com.mgmetehan.credit_module_challenge.dto.response.CreateLoanResponse;
import com.mgmetehan.credit_module_challenge.model.Customer;
import com.mgmetehan.credit_module_challenge.model.Loan;
import com.mgmetehan.credit_module_challenge.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        loan = loanRepository.save(loan);

        UpdateCustomerRequest updateCustomerRequest = CustomerConverter
                .getUpdateCustomerRequest(request, customer);

        customerService.updateCustomer(updateCustomerRequest);

        return createLoanConverter.toResponse(loan);
    }
}