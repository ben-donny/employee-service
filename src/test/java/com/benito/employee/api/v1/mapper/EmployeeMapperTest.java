package com.benito.employee.api.v1.mapper;

import com.benito.employee.api.v1.model.EmployeeDTO;
import com.benito.employee.domain.Employee;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeMapperTest {

    public static long ID = 1L;
    public static final String FIRST_NAME = "Lokesh";
    public static final String LAST_NAME = "Gupta";
    public static final String EMAIL_ADDRESS = "howtodoinjava@gmail.com";

    EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Test
    public void employeeToEmployeeDTO() {

        //given
        Employee employee = new Employee();
        employee.setId(ID);
        employee.setFirstName(FIRST_NAME);
        employee.setLastName(LAST_NAME);
        employee.setEmail(EMAIL_ADDRESS);

        //when
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);

        //then

        assertEquals(Long.valueOf(ID),employee.getId());
        assertEquals(FIRST_NAME,employee.getFirstName() );
        assertEquals(LAST_NAME,employee.getLastName());
        assertEquals(EMAIL_ADDRESS,employee.getEmail() );

    }
}