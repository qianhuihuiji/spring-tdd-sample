package com.nofirst.spring.tdd.sample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nofirst.spring.tdd.sample.domain.Car;
import com.nofirst.spring.tdd.sample.exception.CarNotFoundException;
import com.nofirst.spring.tdd.sample.repository.CarRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration(classes = {CarService.class})
@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    private CarService carService;

    @Before
    public void setUp() {
        carService = new CarService(carRepository);
    }

    @Test
    public void can_get_car_detail_info() {
        given(carRepository.findByName("emen")).willReturn(new Car("emen", "test"));

        Car car = carService.getCarDetail("emen");

        assertThat(car.getName()).isEqualTo("emen");
        assertThat(car.getType()).isEqualTo("test");
    }

    @Test(expected = CarNotFoundException.class)
    public void throw_exception_when_car_not_found() {
        given(carRepository.findByName("emen")).willReturn(null);

        carService.getCarDetail("emen");
    }
}