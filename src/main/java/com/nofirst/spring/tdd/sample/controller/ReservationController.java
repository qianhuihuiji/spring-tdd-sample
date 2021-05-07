package com.nofirst.spring.tdd.sample.controller;

import com.nofirst.spring.tdd.sample.domain.Reservation;
import com.nofirst.spring.tdd.sample.repository.ReservationRepository;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    protected ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping(value = "/reservations",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Collection<Reservation> reservations(){
        return reservationRepository.findAll();
//        return Collections.singletonList(new Reservation(1L,"Jane"));
    }
}
