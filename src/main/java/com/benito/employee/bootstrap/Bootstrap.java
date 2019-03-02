package com.benito.employee.bootstrap;

import com.benito.employee.domain.Employee;
import com.benito.employee.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public Bootstrap(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadEmployee();

    }

    private void loadEmployee() {
        Employee employee1 = new Employee();
        employee1.setFirstName("Lokesh");
        employee1.setLastName("Gupta");
        employee1.setEmail("howtodoinjava@gmail.com");

        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setFirstName("Alex");
        employee2.setLastName("Kolenchiskey");
        employee2.setEmail("abc@gmail.com");

        employeeRepository.save(employee2);

        Employee employee3 = new Employee();
        employee3.setFirstName("David");
        employee3.setLastName("Kameron");
        employee3.setEmail("titanic@gmail.com");

        employeeRepository.save(employee3);

        Employee employee4 = new Employee();
        employee4.setFirstName("Benito");
        employee4.setLastName("Musolini");
        employee4.setEmail("ben.doni@mailinator.com");

        employeeRepository.save(employee4);

        System.out.println("Employee Loaded: " + employeeRepository.count());
    }
}
