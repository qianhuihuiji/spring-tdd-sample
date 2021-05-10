package com.nofirst.spring.tdd.sample.sample5.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nofirst.spring.tdd.sample.sample5.domain.Payment;

public class PaymentRequest {

    private final Payment payment;

    public PaymentRequest(@JsonProperty("payment") Payment payment) {
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "payment=" + payment +
                '}';
    }
}
