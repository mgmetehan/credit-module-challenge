package com.mgmetehan.credit_module_challenge.repository;

import com.mgmetehan.credit_module_challenge.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}