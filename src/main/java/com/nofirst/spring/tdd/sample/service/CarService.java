package com.nofirst.spring.tdd.sample.service;

import com.nofirst.spring.tdd.sample.domain.Car;
import com.nofirst.spring.tdd.sample.exception.CarNotFoundException;
import com.nofirst.spring.tdd.sample.repository.CarRepository;
import java.util.Optional;
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

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car update(Car car) {
        Optional<Car> carOptional = carRepository.findById(car.getId());

        if (! carOptional.isPresent()) {
            throw new CarNotFoundException();
        }

        Car carSaved = carOptional.get();
        carSaved.setName(car.getName());
        carSaved.setType(car.getType());

        return carRepository.save(carSaved);
    }
}
