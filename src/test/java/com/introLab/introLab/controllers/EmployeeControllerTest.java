package com.introLab.introLab.controllers;

import com.introLab.introLab.Enum.Status;
import com.introLab.introLab.model.Employee;
import com.introLab.introLab.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EmployeeRepository employeeRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Employee testEmployee;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //create a doctor un bbdd
        testEmployee = new Employee("Dr. House", "Cardiology", Status.ON);
        employeeRepository.save(testEmployee);
    }

    @AfterEach
    public void tearDown() {
        // Limpiar la base de datos después de cada prueba
        employeeRepository.deleteById(testEmployee.getEmployeeId());
    }

    @Test
    public void getAllEmployeesReturnOk() throws Exception {
        mockMvc.perform(get("/doctors/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getEmployeeByIdReturnEmployee() throws Exception {
        mockMvc.perform(get("/doctors/" + testEmployee.getEmployeeId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Dr. House"));
    }

    @Test
    public void getPatientsByDoctorStatusReturnPatient() throws Exception {
        mockMvc.perform(get("/patients/status/ON"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Amy Pond"));
    }
}