package com.nofirst.spring.tdd.sample.sample3.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.nofirst.spring.tdd.sample.sample3.domain.Todo;
import com.nofirst.spring.tdd.sample.sample3.repository.TodoRepository;
import com.nofirst.spring.tdd.sample.sample3.service.TodoService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {TodoService.class})
@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    private TodoService todoService;

    @Before
    public void setUp(){
        todoService = new TodoService(todoRepository);
    }

    @Test
    public void get_all_todos() {
        // Given
        Todo todoSample1 = new Todo("Todo Sample 1",true);
        Todo todoSample2 = new Todo("Todo Sample 2",true);
        List<Todo> todoListSample = new ArrayList<>();
        todoListSample.add(todoSample1);
        todoListSample.add(todoSample2);
        given(todoRepository.findAll()).willReturn(todoListSample);

        // When
        List<Todo> todoList = todoService.findAll();

        // Then
        assertThat(todoList.size()).isEqualTo(2);
        assertThat(todoList.contains(todoSample1)).isTrue();
        assertThat(todoList.contains(todoSample2)).isTrue();
    }

    @Test
    public void can_save_a_todo() {
        // Given
        Todo todoSample = new Todo("Drink walter", true);
        given(todoRepository.save(todoSample)).willReturn(todoSample);

        // When
        Todo savedTodo = todoService.save(todoSample);

        // Then
        assertThat(savedTodo.getText()).isEqualTo("Drink walter");
        assertThat(savedTodo.isCompleted()).isEqualTo(true);
    }
}