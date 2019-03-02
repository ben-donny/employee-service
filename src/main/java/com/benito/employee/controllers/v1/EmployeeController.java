package com.benito.employee.controllers.v1;

import com.benito.employee.api.v1.model.EmployeeDTO;
import com.benito.employee.api.v1.model.EmployeeListDTO;
import com.benito.employee.repositories.EmployeeRepository;
import com.benito.employee.services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/api/v1/employees") previous implementation
@Api(description = "This is my Employee Controller")
@Controller
@RequestMapping(EmployeeController.BASEURL)
public class EmployeeController {


    private final EmployeeService employeeService;

    public static final String BASEURL = "/api/v1/employees";
    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService) {

        this.employeeService = employeeService;
    }
     @ApiOperation(value = "This will get a list of employees.", notes = "These are some notes about the API.")
     @GetMapping
     public ResponseEntity<EmployeeListDTO> getAllEmployees(){

        return  new ResponseEntity<EmployeeListDTO>(
                new EmployeeListDTO(employeeService.getAllEmployees()), HttpStatus.OK);
     }

     @ApiOperation(value = "Get employee by their first name", notes = "one employee should be returned")
     @GetMapping("/{firstname}/name")
     public ResponseEntity<EmployeeDTO> getEmployeeByFirstName(@PathVariable String firstname){

        return  new ResponseEntity<EmployeeDTO>(employeeService.getEmployeeByFirstName(firstname), HttpStatus.OK);

     }

     @ApiOperation(value = "Get employee by Id", notes = "one employee should be returned")
     @GetMapping("/{id}")
     public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable long id){

        return new ResponseEntity<EmployeeDTO>(employeeService.getEmployeeById(id), HttpStatus.OK);
     }

    @ApiOperation(value = "This will create new employee", notes = "new employee will be created")
    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO employeeDTO){

        return new ResponseEntity<EmployeeDTO>(employeeService.createNewEmployee(employeeDTO),HttpStatus.CREATED);
    }

    @ApiOperation(value = "This will update an existing employee", notes = "employee will be updated")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){

        return new ResponseEntity<EmployeeDTO>(employeeService.saveEmployeeByDTO(id,employeeDTO ), HttpStatus.OK);
    }

    @ApiOperation(value = "This will update a particular field of an employee", notes = "employee should updated")
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDTO> patchEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){

        return new ResponseEntity<EmployeeDTO>(employeeService.patchEmployee(id,employeeDTO ), HttpStatus.OK);
    }

    @ApiOperation(value = "This will delete an existing employee", notes = "new employee will be deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){

        employeeService.deleteEmployeeById(id );

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
