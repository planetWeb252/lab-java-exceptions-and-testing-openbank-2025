package com.introLab.introLab.repository;

import com.introLab.introLab.Enum.Status;
import com.introLab.introLab.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findAll();
    Patient findById(long id);
    List<Patient> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    List<Patient> findPatientsByEmployeeDepartment(String department);

    List<Patient> findPatientsByEmployeeStatusEnum(Status employeeStatusEnum);

}
