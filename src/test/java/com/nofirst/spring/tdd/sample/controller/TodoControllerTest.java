package com.nofirst.spring.tdd.sample.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nofirst.spring.tdd.sample.domain.Todo;
import com.nofirst.spring.tdd.sample.service.TodoService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void can_get_all_todo_list() throws Exception {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo(1L,"Eat once",true));
        todoList.add(new Todo(2L,"Sleep Twice",true));

        given(todoService.findAll()).willReturn(todoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    public void create_a_todo_successfully() throws Exception {
        // Given
        Todo eatTodo = new Todo("Eat food", false);
        given(todoService.save(eatTodo)).willReturn(eatTodo);

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = objectMapper.writeValueAsString(eatTodo);

        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON)
        );

        // Then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("Eat food"))
                .andExpect(jsonPath("$.completed").value(false));
    }
}
