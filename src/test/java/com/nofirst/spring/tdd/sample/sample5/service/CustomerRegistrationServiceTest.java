package com.nofirst.spring.tdd.sample.sample5.service;

import com.nofirst.spring.tdd.sample.sample5.domain.Customer;
import com.nofirst.spring.tdd.sample.sample5.repository.CustomerRepository;
import com.nofirst.spring.tdd.sample.sample5.request.CustomerRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

public class CustomerRegistrationServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    private CustomerRegistrationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new CustomerRegistrationService(customerRepository);
    }

    @Test
    public void can_save_new_customer() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(UUID.randomUUID(), "Maryam", phoneNumber);

        // 假设数据库里没有与 customer 相同的数据
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.empty());

        // When
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(customer);
    }

    @Test
    void save_new_customer_when_id_is_null() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(null, "Maryam", phoneNumber);

        // ... No customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.empty());

        // When
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(customer, "id");
        assertThat(customerArgumentCaptorValue.getId()).isNotNull();
    }

    @Test
    void not_save_customer_when_customer_name_has_already_exists() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(UUID.randomUUID(), "Maryam", phoneNumber);

        // 假设该 customer 已经存在
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customer));

        // When
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should(never()).save(any());
    }

    @Test
    void throw_an_exception_when_phone_number_is_taken() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(UUID.randomUUID(), "Maryam", phoneNumber);
        Customer customerTwo = new Customer(UUID.randomUUID(), "John", phoneNumber);

        // 假设该 customer 不存在
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customerTwo));

        // When
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);
        // Then
        assertThatThrownBy(() -> underTest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("phone number [%s] is taken", phoneNumber));

        // Then...
        then(customerRepository).should(never()).save(any(Customer.class));

    }
}
