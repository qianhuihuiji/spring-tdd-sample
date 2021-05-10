package com.nofirst.spring.tdd.sample.sample2.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nofirst.spring.tdd.sample.sample2.domain.Reservation;
import com.nofirst.spring.tdd.sample.sample2.repository.ReservationRepository;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void find_by_reservation_name() {
        Reservation savedReservation = entityManager.persistAndFlush(new Reservation("Jane"));
        assertThat(savedReservation.getId()).isNotNull().isNotNegative();
        Collection<Reservation> reservations = reservationRepository.findByReservationName("Jane");
        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations.contains(savedReservation)).isTrue();
    }
}
