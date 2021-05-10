package com.nofirst.spring.tdd.sample.sample5.service;

import com.nofirst.spring.tdd.sample.sample5.domain.CardPaymentCharge;
import com.nofirst.spring.tdd.sample.sample5.domain.Currency;
import java.math.BigDecimal;

public interface CardPaymentCharger {
    CardPaymentCharge chargeCard(String cardSource, BigDecimal amount, Currency currency, String description);
}
