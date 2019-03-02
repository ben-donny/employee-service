package com.benito.employee.services;

import com.benito.employee.api.v1.model.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeByFirstName(String firstname);

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO saveEmployeeByDTO(Long id, EmployeeDTO employeeDTO);

    EmployeeDTO patchEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployeeById(Long id);


}
