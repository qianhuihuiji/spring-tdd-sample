package com.nofirst.spring.tdd.sample.repository;

import com.nofirst.spring.tdd.sample.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
