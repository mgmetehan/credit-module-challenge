package com.mgmetehan.credit_module_challenge.service;

import com.mgmetehan.credit_module_challenge.converter.CustomerConverter;
import com.mgmetehan.credit_module_challenge.converter.LoanConverter;
import com.mgmetehan.credit_module_challenge.dto.request.CreateCustomerDTO;
import com.mgmetehan.credit_module_challenge.dto.request.UpdateCreditLimitDTO;
import com.mgmetehan.credit_module_challenge.dto.request.UpdateCustomerRequest;
import com.mgmetehan.credit_module_challenge.dto.response.CustomerResponseDTO;
import com.mgmetehan.credit_module_challenge.dto.response.ResponseLoanDTO;
import com.mgmetehan.credit_module_challenge.model.Customer;
import com.mgmetehan.credit_module_challenge.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponseDTO createCustomer(CreateCustomerDTO requestDTO) {
        Customer customer = CustomerConverter.toEntity(requestDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerConverter.toResponseDTO(savedCustomer);
    }

    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerFindById(id);
        return CustomerConverter.toResponseDTO(customer);
    }

    public CustomerResponseDTO updateCreditLimit(Long id, UpdateCreditLimitDTO requestDTO) {
        Customer customer = customerFindById(id);
        customer.setCreditLimit(requestDTO.getCreditLimit());
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerConverter.toResponseDTO(updatedCustomer);
    }

    public Customer customerFindById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerFindById(id);
        customer.setDeleted(true);
        customerRepository.save(customer);
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerConverter::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerResponseDTO updateCustomer(UpdateCustomerRequest request) {
        Customer customer = customerFindById(request.getId());
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setCreditLimit(request.getCreditLimit());
        customer.setUsedCreditLimit(request.getUsedCreditLimit());
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerConverter.toResponseDTO(updatedCustomer);
    }

    public List<ResponseLoanDTO> getCustomerLoans(Long customerId) {
        Customer customer = customerFindById(customerId);

        return new ArrayList<>(customer.getLoans()).stream()
                .map(LoanConverter::toLoanResponse)
                .collect(Collectors.toList());
    }
}
