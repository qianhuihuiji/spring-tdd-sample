package com.nofirst.spring.tdd.sample.sample3.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nofirst.spring.tdd.sample.sample3.domain.Todo;
import com.nofirst.spring.tdd.sample.sample3.repository.TodoRepository;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void find_all() {
        Todo savedTodo = entityManager.persistAndFlush(new Todo("Eat food", true));
        assertThat(savedTodo.getId()).isNotNull().isNotNegative();
        List<Todo> all = todoRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.contains(savedTodo)).isTrue();
    }
}
