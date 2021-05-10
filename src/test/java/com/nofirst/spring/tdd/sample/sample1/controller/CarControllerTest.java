package com.nofirst.spring.tdd.sample.sample1.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nofirst.spring.tdd.sample.sample1.domain.Car;
import com.nofirst.spring.tdd.sample.sample1.exception.CarNotFoundException;
import com.nofirst.spring.tdd.sample.sample1.service.CarService;
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
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void can_get_a_car() throws Exception {
        given(carService.getCarDetail(anyString()))
                .willReturn(new Car("emen", "test"));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/emen"))
                .andExpect(status().isOk()).andExpect(jsonPath("name").value("emen"))
                .andExpect(jsonPath("type").value("test"));
    }

    @Test
    public void get_exception_if_we_request_a_not_existing_car() throws Exception {
        given(carService.getCarDetail(anyString()))
                .willThrow(new CarNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/not-a-car"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void create_a_car_successfully() throws Exception {
        // Given
        Car car = new Car("Benz", "Sports car");
        given(carService.save(car)).willReturn(car);

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = objectMapper.writeValueAsString(car);

        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatToDoJSON)
        );

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Benz"))
                .andExpect(jsonPath("$.type").value("Sports car"));
    }

    @Test
    public void update_a_car_successfully() throws Exception {
        // Given
        Car car = new Car(1L, "Benz", "Sports car");
        given(carService.update(car)).willReturn(car);

        ObjectMapper objectMapper = new ObjectMapper();
        String carJSON = objectMapper.writeValueAsString(car);

        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carJSON)
        );

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Benz"))
                .andExpect(jsonPath("$.type").value("Sports car"));
    }

    @Test
    public void get_exception_if_we_update_a_not_existing_car() throws Exception {
        // Given
        given(carService.update(any()))
                .willThrow(new CarNotFoundException());
        Car car = new Car(1L, "Benz", "Sports car");
        ObjectMapper objectMapper = new ObjectMapper();
        String carJSON = objectMapper.writeValueAsString(car);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJSON)
                ).andExpect(status().isNotFound());
    }
}
