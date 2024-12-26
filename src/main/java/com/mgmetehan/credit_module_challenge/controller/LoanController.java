package com.mgmetehan.credit_module_challenge.controller;

import com.mgmetehan.credit_module_challenge.dto.request.CreateLoanRequest;
import com.mgmetehan.credit_module_challenge.dto.response.CreateLoanResponse;
import com.mgmetehan.credit_module_challenge.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<CreateLoanResponse> createLoan(@RequestBody @Valid CreateLoanRequest request) {
        return ResponseEntity.ok(loanService.createLoan(request));
    }
} 