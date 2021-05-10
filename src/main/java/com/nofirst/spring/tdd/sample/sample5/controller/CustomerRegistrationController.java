package com.nofirst.spring.tdd.sample.sample5.controller;

import com.nofirst.spring.tdd.sample.sample5.request.CustomerRegistrationRequest;
import com.nofirst.spring.tdd.sample.sample5.service.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRegistrationController {

    private final CustomerRegistrationService customerRegistrationService;

    @Autowired
    public CustomerRegistrationController(CustomerRegistrationService customerRegistrationService) {
        this.customerRegistrationService = customerRegistrationService;
    }

    @PostMapping("api/v1/customer-registration")
    public void registerNewCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerRegistrationService.registerNewCustomer(request);
    }
}
