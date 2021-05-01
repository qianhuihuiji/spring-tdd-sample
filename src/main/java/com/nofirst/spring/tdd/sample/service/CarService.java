package com.nofirst.spring.tdd.sample.service;

import com.nofirst.spring.tdd.sample.domain.Car;
import com.nofirst.spring.tdd.sample.exception.CarNotFoundException;
import com.nofirst.spring.tdd.sample.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Cacheable("cars")
    public Car getCarDetail(String carName) throws CarNotFoundException {
        Car car = carRepository.findByName(carName);
        if (car == null) {
            throw new CarNotFoundException();
        }
        return car;
    }
}
