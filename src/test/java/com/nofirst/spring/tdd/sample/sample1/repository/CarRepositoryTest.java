package com.nofirst.spring.tdd.sample.sample1.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nofirst.spring.tdd.sample.sample1.domain.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void find_by_name() {
        Car savedCar = entityManager.persistAndFlush(new Car("emen", "test"));
        assertThat(savedCar.getId()).isNotNull().isNotNegative();
        Car car = carRepository.findByName("emen");

        assertThat(car.getName()).isEqualTo("emen");
    }
}
