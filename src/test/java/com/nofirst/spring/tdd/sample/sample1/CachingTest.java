package com.nofirst.spring.tdd.sample.sample1;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nofirst.spring.tdd.sample.sample1.domain.Car;
import com.nofirst.spring.tdd.sample.sample1.repository.CarRepository;
import com.nofirst.spring.tdd.sample.sample1.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestDatabase
@AutoConfigureCache
public class CachingTest {

    @Autowired
    private CarService service;

    @MockBean
    private CarRepository carRepository;

    @Test
    public void suer_cache_data_when_request_same_car() {
        given(carRepository.findByName(anyString()))
                .willReturn(new Car("emen", "test"));

        service.getCarDetail("emen");

        verify(carRepository, times(1)).findByName("emen");
    }
}
