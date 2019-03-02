package com.benito.employee.services;

import com.benito.employee.api.v1.mapper.EmployeeMapper;
import com.benito.employee.api.v1.model.EmployeeDTO;
import com.benito.employee.domain.Employee;
import com.benito.employee.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private  final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper, EmployeeRepository employeeRepository) {
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeByFirstName(String firstname) {
        return employeeMapper.employeeToEmployeeDTO(employeeRepository.findByFirstName(firstname));
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeMapper.employeeToEmployeeDTO(employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        return saveAndReturnedDTO(employeeMapper.employeeDtoToEmployee(employeeDTO));
    }

    private EmployeeDTO saveAndReturnedDTO(Employee employee) {

        Employee savedEmployee = employeeRepository.save(employee);

       EmployeeDTO returnedDto = employeeMapper.employeeToEmployeeDTO(savedEmployee);

       return returnedDto;
    }


    @Override
    public EmployeeDTO saveEmployeeByDTO(Long id, EmployeeDTO employeeDTO) {

        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDTO);
        employee.setId(id);
        return  saveAndReturnedDTO(employee);

    }

    @Override
    public EmployeeDTO patchEmployee(Long id, EmployeeDTO employeeDTO) {
        return employeeRepository.findById(id).map(employee -> {

            if (employeeDTO.getFirstName() !=null){
                employee.setFirstName(employeeDTO.getFirstName());
            }

            if (employeeDTO.getLastName() != null){
                employee.setLastName(employeeDTO.getLastName());

            }

            if (employeeDTO.getEmail() != null){
                employee.setEmail(employeeDTO.getEmail());
            }

            EmployeeDTO returnedDto = employeeMapper.employeeToEmployeeDTO(employeeRepository.save(employee));

            return returnedDto;
        }).orElseThrow(ResourceNotFoundException::new); // todo implement better except handling;
    }

    @Override
    public void deleteEmployeeById(Long id) {

        employeeRepository.deleteById(id);

    }
}
