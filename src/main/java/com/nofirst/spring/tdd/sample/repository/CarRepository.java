package com.nofirst.spring.tdd.sample.repository;

import com.nofirst.spring.tdd.sample.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByName(String name);
}
