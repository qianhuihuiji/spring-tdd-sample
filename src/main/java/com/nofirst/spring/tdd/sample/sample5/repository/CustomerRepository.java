package com.nofirst.spring.tdd.sample.sample5.repository;

import com.nofirst.spring.tdd.sample.sample5.domain.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query(
            value = "select id, name, phone_number " +
                    "from customer where phone_number = :phone_number",
            nativeQuery = true
    )
    Optional<Customer> selectCustomerByPhoneNumber(@Param("phone_number") String phoneNumber);
}
