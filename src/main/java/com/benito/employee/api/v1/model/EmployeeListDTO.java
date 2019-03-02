package com.benito.employee.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeListDTO {

    List<EmployeeDTO> employees;
}
