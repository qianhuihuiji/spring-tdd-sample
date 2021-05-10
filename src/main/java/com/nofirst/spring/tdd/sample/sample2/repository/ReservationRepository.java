package com.nofirst.spring.tdd.sample.sample2.repository;

import com.nofirst.spring.tdd.sample.sample2.domain.Reservation;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Collection<Reservation> findByReservationName(String reservationName);
}
