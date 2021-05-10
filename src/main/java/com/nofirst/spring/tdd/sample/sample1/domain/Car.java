package com.nofirst.spring.tdd.sample.sample1.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String type;

    public Car(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
