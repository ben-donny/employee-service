package com.benito.employee.repositories;

import com.benito.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByFirstName(String firstName);
}
