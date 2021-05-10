package com.nofirst.spring.tdd.sample.sample1.controller;

import com.nofirst.spring.tdd.sample.sample1.domain.Car;
import com.nofirst.spring.tdd.sample.sample1.exception.CarNotFoundException;
import com.nofirst.spring.tdd.sample.sample1.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars/{name}")
    public Car getCar(@PathVariable("name") String carName) {
        return carService.getCarDetail(carName);
    }

    @PostMapping("/cars")
    public Car save(@RequestBody Car car) {
        return carService.save(car);
    }

    @PutMapping("/cars")
    public Car updateById(@RequestBody Car car) {
        return carService.update(car);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void carNotFundHandler(CarNotFoundException ex) {
        log.error("car not found!");
    }
}
