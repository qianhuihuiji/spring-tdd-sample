package com.nofirst.spring.tdd.sample.sample3.controller;

import com.nofirst.spring.tdd.sample.sample3.domain.Todo;
import com.nofirst.spring.tdd.sample.sample3.service.TodoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    List<Todo> getAllTodoList() {
        return todoService.findAll();
    }

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    Todo create(@RequestBody Todo todo) {
        return todoService.save(todo);
    }
}
