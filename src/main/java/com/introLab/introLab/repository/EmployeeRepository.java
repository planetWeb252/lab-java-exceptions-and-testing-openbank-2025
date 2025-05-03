package com.introLab.introLab.repository;

import com.introLab.introLab.Enum.Status;
import com.introLab.introLab.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAll();
    Employee findByEmployeeId(int employeeId);
    List<Employee> findByStatusEnum(Status status);
    List<Employee> findByDepartment(String department);
}
