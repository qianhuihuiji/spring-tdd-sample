package com.nofirst.spring.tdd.sample.sample3.repository;

import com.nofirst.spring.tdd.sample.sample3.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
