package com.nofirst.spring.tdd.sample.sample5.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nofirst.spring.tdd.sample.sample5.domain.Customer;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test; // JUnit 5
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void it_should_select_customer_by_phone_number() {
        // Given
        UUID id = UUID.randomUUID();
        String phoneNumber = "0000";
        Customer customer = new Customer(id, "Abel", phoneNumber);
        customerRepository.save(customer);

        // When
        Optional<Customer> optionalCustomer = customerRepository.selectCustomerByPhoneNumber(phoneNumber);

        // Then
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).isEqualTo(customer);
                });
    }

    @Test
    public void can_not_select_customer_by_phone_number_when_number_does_not_exist() {
        // Given
        String phoneNumber = "0000";

        // When
        Optional<Customer> optionalCustomer = customerRepository.selectCustomerByPhoneNumber(phoneNumber);

        // Then
        assertThat(optionalCustomer).isNotPresent();
    }

    @Test
    public void can_save_customer() {
        // Given
        UUID id = UUID.randomUUID();
        String phoneNumber = "0000";
        Customer customer = new Customer(id, "Abel", phoneNumber);

        // When
        customerRepository.save(customer);

        // Then
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        // 方法一
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo("Abel");
                    assertThat(c.getPhoneNumber()).isEqualTo(phoneNumber);
                });
        // 方法二
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }

    @Test
    public void can_not_save_customer_when_name_is_null() {
        // Given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, null, "0000");

        // When
        // Then
        assertThatThrownBy(() -> customerRepository.save(customer))
                .hasMessageContaining("not-null property references a null or transient value : com.nofirst.spring.tdd.sample.sample5.domain.Customer.name")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void can_not_save_customer_when_phone_number_is_null() {
        // Given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "alex", null);

        // When
        // Then
        assertThatThrownBy(() -> customerRepository.save(customer))
                .hasMessageContaining("not-null property references a null or transient value : com.nofirst.spring.tdd.sample.sample5.domain.Customer.phoneNumber")
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
