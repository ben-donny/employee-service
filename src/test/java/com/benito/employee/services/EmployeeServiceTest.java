package com.benito.employee.services;

import com.benito.employee.api.v1.mapper.EmployeeMapper;
import com.benito.employee.api.v1.model.EmployeeDTO;
import com.benito.employee.domain.Employee;
import com.benito.employee.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {


    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Benito";

    EmployeeService employeeService;

    EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Mock
    EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        //employeeService = new EmployeeServiceImpl(EmployeeMapper.INSTANCE, employeeRepository);

        employeeService = new EmployeeServiceImpl(employeeMapper, employeeRepository);
    }

    @Test
    public void getAllEmployees() throws Exception{

        //given

        List<Employee> employees = Arrays.asList(new Employee(), new Employee(), new Employee());

        when(employeeRepository.findAll()).thenReturn(employees);

        //when

        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployees();

        //then

        assertEquals(3,employeeDTOS.size() );
    }


    @Test
    public void findAllEmployees() throws Exception{

        //given

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Michale");
        employee1.setLastName("Weston");
        employee1.setEmail("michale.weston@yahoo.com");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Sam");
        employee2.setLastName("Axe");
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        //when

        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployees();

        //then

        assertEquals(2,employeeDTOS.size() );
    }


    @Test
    public void getEmployeeByFirstName() throws Exception {

        //given

        Employee employee = new Employee();
        employee.setId(ID);
        employee.setFirstName(FIRST_NAME);

        when(employeeRepository.findByFirstName(anyString()))
                .thenReturn(employee);

        //when
        EmployeeDTO employeeDTO = employeeService.getEmployeeByFirstName(FIRST_NAME);

        //then
        assertEquals(ID,employeeDTO.getId());
        assertEquals(FIRST_NAME,employeeDTO.getFirstName());
    }


    @Test
    public void getEmployeeById() throws Exception {

        //given

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("James Degal");
        employee1.setLastName("Johnson Matthew");

        when(employeeRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.ofNullable(employee1));

        //when
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(1L);

        //then
        assertEquals("James Degal",employeeDTO.getFirstName());
        assertEquals("Johnson Matthew",employeeDTO.getLastName());
    }

    @Test
    public void createNewEmployee() throws Exception {

        //given

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setFirstName("Jim");
        employeeDTO.setLastName("Chris");

        Employee savedEmployee = new Employee();
        savedEmployee.setFirstName(employeeDTO.getFirstName());
        savedEmployee.setLastName(employeeDTO.getLastName());
        savedEmployee.setId(1L);
        savedEmployee.setId(savedEmployee.getId());

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        //when
        EmployeeDTO savedDto = employeeService.createNewEmployee(employeeDTO);

        //then

        assertEquals(employeeDTO.getFirstName(), savedDto.getFirstName());
        assertEquals("Chris", savedDto.getLastName());
    }

    @Test
    public void savedEmployeeByDTO() throws Exception {

        //given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setFirstName("Jim");
        employeeDTO.setEmail("Kate.moc@yahoo.com");

        Employee savedEmployee = new Employee();
        savedEmployee.setFirstName(employeeDTO.getFirstName());
        savedEmployee.setLastName(employeeDTO.getLastName());
        savedEmployee.setId(employeeDTO.getId());
        savedEmployee.setEmail(employeeDTO.getEmail());


        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        //when
        EmployeeDTO savedDto = employeeService.saveEmployeeByDTO(1L,employeeDTO );

        //then

        assertEquals(employeeDTO.getFirstName(),savedDto.getFirstName() );
        assertEquals(employeeDTO.getEmail(), savedDto.getEmail());
    }

    @Test
    public void deleteEmployeeById() throws Exception {

        Long id = 1L;

        employeeService.deleteEmployeeById(id);

        verify(employeeRepository, times(1)).deleteById(anyLong());
    }
}