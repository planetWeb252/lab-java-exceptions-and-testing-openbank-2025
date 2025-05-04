package com.introLab.introLab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.introLab.introLab.Enum.Status;
import com.introLab.introLab.model.Employee;
import com.introLab.introLab.model.Patient;
import com.introLab.introLab.repository.EmployeeRepository;
import com.introLab.introLab.repository.PatientRepository;

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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class PatientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Employee testDoctor;
    private Patient testPatient;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        testDoctor = new Employee("Dr. Who", "Neurology", Status.OFF);
        employeeRepository.save(testDoctor);

        testPatient = new Patient("Amy Pond", LocalDate.of(1990, 5, 1), testDoctor);
        patientRepository.save(testPatient);
    }

    @AfterEach
    public void tearDown() {
        // Limpiar la base de datos después de cada prueba
        if (testPatient.getId() != null) {
            patientRepository.deleteById(testPatient.getId());
            employeeRepository.deleteById(testDoctor.getEmployeeId());
        }


    }

    @Test
    public void getAllPatients_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/patients/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getPatientById_shouldReturnPatient() throws Exception {
        mockMvc.perform(get("/patients/" + testPatient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Amy Pond"));
    }

    @Test
    public void getPatientsByDateOfBirthRange_shouldReturnPatient() throws Exception {
        mockMvc.perform(get("/patients/dateofbirth")
                        .param("startDate", "1980-01-01")
                        .param("endDate", "2000-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Amy Pond"));
    }

    @Test
    public void getPatientsByDepartment_shouldReturnPatient() throws Exception {
        mockMvc.perform(get("/patients/department/Neurology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Amy Pond"));
    }

    @Test
    public void getPatientsByDoctorStatus_shouldReturnPatient() throws Exception {
        mockMvc.perform(get("/patients/status/'OFF'"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Amy Pond"));
    }
}
