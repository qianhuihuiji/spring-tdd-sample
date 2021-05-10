package com.nofirst.spring.tdd.sample.sample5.repository;

import com.nofirst.spring.tdd.sample.sample5.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
