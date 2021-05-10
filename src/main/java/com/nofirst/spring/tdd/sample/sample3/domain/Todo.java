package com.nofirst.spring.tdd.sample.sample3.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Todo {

    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private boolean completed;

    public Todo(String text, boolean completed) {
        this.text = text;
        this.completed = completed;
    }
}
