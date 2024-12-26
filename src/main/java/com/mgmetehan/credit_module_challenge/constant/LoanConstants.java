package com.mgmetehan.credit_module_challenge.constant;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public final class LoanConstants {
    public static final Map<Integer, Double> INTEREST_RATES = Map.of(
            6, 0.1,
            9, 0.2,
            12, 0.3,
            24, 0.5
    );
} 