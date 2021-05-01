package com.nofirst.spring.tdd.sample.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nofirst.spring.tdd.sample.domain.Car;
import com.nofirst.spring.tdd.sample.exception.CarNotFoundException;
import com.nofirst.spring.tdd.sample.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
}
