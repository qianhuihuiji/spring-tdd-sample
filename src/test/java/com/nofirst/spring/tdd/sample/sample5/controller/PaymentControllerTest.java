package com.nofirst.spring.tdd.sample.sample5.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nofirst.spring.tdd.sample.sample5.domain.CardPaymentCharge;
import com.nofirst.spring.tdd.sample.sample5.domain.Currency;
import com.nofirst.spring.tdd.sample.sample5.domain.Customer;
import com.nofirst.spring.tdd.sample.sample5.domain.Payment;
import com.nofirst.spring.tdd.sample.sample5.repository.CustomerRepository;
import com.nofirst.spring.tdd.sample.sample5.repository.PaymentRepository;
import com.nofirst.spring.tdd.sample.sample5.request.PaymentRequest;
import com.nofirst.spring.tdd.sample.sample5.service.CardPaymentCharger;
import com.nofirst.spring.tdd.sample.sample5.service.PaymentService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardPaymentCharger cardPaymentCharger;

    @Test
    void can_create_payment_successfully() throws Exception {
        // Given a customer
        String phoneNumber = "00000";
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "Maryam", phoneNumber);

        customerRepository.save(customer);

        // ... Payment
        long paymentId = 1L;
        Payment payment = new Payment(paymentId, customerId, new BigDecimal("100.00"), Currency.GBP, "x0x0x0x0", "Zakat");

        // ... Payment request
        PaymentRequest paymentRequest = new PaymentRequest(payment);

        // 模拟 Stripe 的真实调用
        given(cardPaymentCharger.chargeCard(paymentRequest.getPayment().getSource(), paymentRequest.getPayment().getAmount(), paymentRequest.getPayment().getCurrency(), paymentRequest.getPayment().getDescription()))
                .willReturn(new CardPaymentCharge(true));

        // ... When payment is sent
        ResultActions paymentResultActions = mockMvc.perform(post("/api/v1/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(paymentRequest))));

        // Then
        paymentResultActions.andExpect(status().isOk());

        // Payment is stored in db
        // TODO: Do not use paymentRepository instead create an endpoint to retrieve payments for customers
        assertThat(paymentRepository.findById(paymentId))
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p).isEqualToComparingFieldByField(payment));
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
