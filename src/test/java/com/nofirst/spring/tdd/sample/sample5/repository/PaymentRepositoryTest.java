package com.nofirst.spring.tdd.sample.sample5.repository;

import com.nofirst.spring.tdd.sample.sample5.domain.Currency;
import com.nofirst.spring.tdd.sample.sample5.domain.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository underTest;

    @Test
    void it_should_insert_payment() {
        // Given
        long paymentId = 1L;
        Payment payment = new Payment(null, UUID.randomUUID(), new BigDecimal("10.00"), Currency.USD, "card123", "Donation");

        // When
        underTest.save(payment);

        // Then
        Optional<Payment> paymentOptional = underTest.findById(paymentId);
        assertThat(paymentOptional)
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p).isEqualTo(payment));
    }
}
