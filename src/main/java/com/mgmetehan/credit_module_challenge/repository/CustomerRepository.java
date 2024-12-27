package com.mgmetehan.credit_module_challenge.repository;

import com.mgmetehan.credit_module_challenge.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.loans WHERE c.id = :customerId")
    Optional<Customer> findCustomerWithLoansById(@Param("customerId") Long customerId);
}
