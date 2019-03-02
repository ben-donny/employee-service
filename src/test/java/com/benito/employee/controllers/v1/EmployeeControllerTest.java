package com.benito.employee.controllers.v1;

import com.benito.employee.api.v1.model.EmployeeDTO;
import com.benito.employee.controllers.RestResponseEntityExceptionHandler;
import com.benito.employee.services.EmployeeService;
import com.benito.employee.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest extends AbstractRestControllerTest {

    public static final String FIRST_NAME = "Benito";

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeeController employeeController;

    MockMvc mockMvc;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void getAllEmployees() throws Exception {

        EmployeeDTO employee1 = new EmployeeDTO();

        employee1.setId(1L);
        employee1.setFirstName("Lokesh");
        employee1.setLastName("Gupta");
        employee1.setEmail("howtodoinjava@gmail.com");



        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setId(2L);
        employee2.setFirstName("Alex");
        employee2.setLastName("Kolenchiskey");
        employee2.setEmail("abc@gmail.com");

        EmployeeDTO employee3 = new EmployeeDTO();
        employee3.setId(3L);
        employee3.setFirstName("David");
        employee3.setLastName("Kameron");
        employee3.setEmail("titanic@gmail.com");

        List<EmployeeDTO> employees = Arrays.asList(employee1, employee2, employee3);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get(EmployeeController.BASEURL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees", hasSize(3)));



    }

    @Test
    public void getEmployeeByFirstName() throws Exception {

        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setId(1L);
        employee1.setFirstName(FIRST_NAME);

        when(employeeService.getEmployeeByFirstName(anyString())).thenReturn(employee1);

        mockMvc.perform(get("/api/v1/employees/Benito/name")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)));
    }

    @Test
    public void getEmployeeById() throws Exception {

         //given
        EmployeeDTO employeeId= new EmployeeDTO();
        employeeId.setId(1L);
        employeeId.setFirstName("James");
        employeeId.setLastName("Mark");
        employeeId.setEmail("someemail@yahoo.com");

        when(employeeService.getEmployeeById(anyLong())).thenReturn(employeeId);

        ///when
        mockMvc.perform(get("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("James")));
    }

    @Test
    public void createNewEmployee() throws Exception {
        //given
        EmployeeDTO employee = new EmployeeDTO();
        employee.setFirstName("Fred");
        employee.setLastName("Flinstone");
        employee.setEmail("fred.flinstone@yahoo.com");

        EmployeeDTO returnDTO = new EmployeeDTO();
        returnDTO.setFirstName(employee.getFirstName());
        returnDTO.setLastName(employee.getLastName());
        returnDTO.setEmail(employee.getEmail());

        when(employeeService.createNewEmployee(employee)).thenReturn(returnDTO);

        //when/then

        mockMvc.perform(post("/api/v1/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.email", equalTo("fred.flinstone@yahoo.com")));

    }

    @Test
    public void testUpdateEmployee() throws Exception {

        //given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Fred");
        employeeDTO.setLastName("Flinstone");
        employeeDTO.setEmail("fred.flinstone@yahoo.com");

        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        employeeDTO1.setFirstName(employeeDTO.getFirstName());
        employeeDTO1.setLastName(employeeDTO.getLastName());
        employeeDTO1.setEmail(employeeDTO.getEmail());

        when(employeeService.saveEmployeeByDTO(anyLong(), any(EmployeeDTO.class) )).thenReturn(employeeDTO1);

        //when/then

        mockMvc.perform(put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flinstone") ))
                .andExpect(jsonPath("$.email", equalTo("fred.flinstone@yahoo.com")));
    }


    @Test
    public void testPatchEmployee() throws  Exception {

      //given

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Fred");

        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        employeeDTO1.setFirstName(employeeDTO.getFirstName());
        employeeDTO1.setLastName("Flinstone");
        employeeDTO1.setEmail("fred.flinstone@yahoo.com");

        when(employeeService.patchEmployee(anyLong(),any(EmployeeDTO.class) )).thenReturn(employeeDTO1);

        mockMvc.perform(patch("/api/v1/employees/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flinstone")))
                .andExpect(jsonPath("$.email", equalTo("fred.flinstone@yahoo.com")))
        ;

    }

    @Test
    public void testDeleteEmployee() throws Exception {

        mockMvc.perform(delete("/api/v1/employees/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

        verify(employeeService).deleteEmployeeById(anyLong());

    }

    @Test
    public void testNotFoundException() throws Exception{

        when(employeeService.getEmployeeById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/api/v1/employees/22")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

}