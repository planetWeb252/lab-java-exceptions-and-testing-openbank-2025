package com.introLab.introLab.controllers;

import com.introLab.introLab.Enum.Status;
import com.introLab.introLab.model.Employee;
import com.introLab.introLab.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class EmployeeController {
    //imports repository
    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //Get all doctors: Create a route to get all doctors.
    @GetMapping("/all")
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

//    Get doctor by ID: Create a route to get a doctor by employee_id.

    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId) {
        return employeeRepository.findByEmployeeId( employeeId);
    }

//    Get doctors by status: Create a route to get doctors by status.
    @GetMapping("/status/{status}")
    public List<Employee> getEmployeesByStatus(@PathVariable Status status) {
        return employeeRepository.findByStatusEnum(status);
    }

//    Get doctors by department: Create a route to get doctors by department.
    @GetMapping("/department/{department}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String department) {
        return employeeRepository.findByDepartment(department);
    }


}
