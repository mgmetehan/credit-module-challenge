package com.mgmetehan.credit_module_challenge.repository;

import com.mgmetehan.credit_module_challenge.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
