package com.nofirst.spring.tdd.sample.sample5.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nofirst.spring.tdd.sample.sample5.domain.Customer;
import com.nofirst.spring.tdd.sample.sample5.repository.CustomerRepository;
import com.nofirst.spring.tdd.sample.sample5.request.CustomerRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerRegistrationControllerTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void customer_can_register_successfully() throws Exception {
        // Given a customer
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "James", "0000000");

        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(customer);

        // When
        ResultActions customerRegResultActions = mockMvc.perform(post("/api/v1/customer-registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(customerRegistrationRequest))));

        // Then
        customerRegResultActions.andExpect(status().isOk());

        // Payment is stored in db
        // TODO: Do not use paymentRepository instead create an endpoint to retrieve payments for customers
        assertThat(customerRepository.findById(customerId))
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p).isEqualTo(customer));
    }

    private String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to json");
            return null;
        }
    }
}
