package com.benito.employee.services;

import com.benito.employee.api.v1.mapper.EmployeeMapper;
import com.benito.employee.api.v1.model.EmployeeDTO;
import com.benito.employee.bootstrap.Bootstrap;
import com.benito.employee.domain.Employee;
import com.benito.employee.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeServiceTestIT {

    @Autowired
    EmployeeRepository employeeRepository;

    EmployeeService employeeService;

    @Before
    public void setUp() throws  Exception {

        System.out.println("Loading Employee Data");
        System.out.println(employeeRepository.findAll().size());

        //setup data for testing

        Bootstrap bootstrap = new Bootstrap(employeeRepository);
        bootstrap.run(); //load data

        employeeService = new EmployeeServiceImpl(EmployeeMapper.INSTANCE, employeeRepository);

    }

    @Test
    public void patchEmployeeUpdateFirstName() throws Exception {

        String updateName = "UpdatedName";
        Long id = getEmployeeIdValue();

        Employee originalEmployee = employeeRepository.getOne(id);
        assertNotNull(originalEmployee);

        //save original first name

        String originalFirstName = originalEmployee.getFirstName();
        String originalLastName = originalEmployee.getLastName();

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName(updateName);

        employeeService.patchEmployee(id,employeeDTO);

        Employee updateEmployee = employeeRepository.findById(id).get();

        assertNotNull(updateEmployee);
        assertEquals(updateName, updateEmployee.getFirstName());
        assertThat(originalFirstName,not(equalTo(updateEmployee.getFirstName())));
        assertThat(originalLastName, equalTo(updateEmployee.getLastName()) );
    }

    @Test
    public void patchEmployeeUpdateLastName() throws Exception {

        String updatedName = "UpdatedName";
        Long id = getEmployeeIdValue();

        Employee originalEmployee = employeeRepository.getOne(id);
        assertNotNull(originalEmployee);

        //saved original first/last name
        String originalFirstName = originalEmployee.getFirstName();
        String originalLastName = originalEmployee.getLastName();

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setLastName(updatedName);

        employeeService.patchEmployee(id,employeeDTO );

        Employee updatedEmployee = employeeRepository.findById(id).get();

        assertNotNull(updatedEmployee);
        assertEquals(updatedName,updatedEmployee.getLastName() );
        assertThat(originalFirstName,equalTo(updatedEmployee.getFirstName()));
        assertThat(originalLastName, not(equalTo(updatedEmployee.getLastName())) );
    }

    private Long getEmployeeIdValue() {

        List<Employee> employees = employeeRepository.findAll();

        System.out.println("Employee Found: " + employees.size());

        return employees.get(0).getId();
    }
}
