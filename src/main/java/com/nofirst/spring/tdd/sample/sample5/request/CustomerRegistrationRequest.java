package com.nofirst.spring.tdd.sample.sample5.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nofirst.spring.tdd.sample.sample5.domain.Customer;

public class CustomerRegistrationRequest {

    private final Customer customer;

    public CustomerRegistrationRequest(
            @JsonProperty("customer") Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "CustomerRegistrationRequest{" +
                "customer=" + customer +
                '}';
    }
}
