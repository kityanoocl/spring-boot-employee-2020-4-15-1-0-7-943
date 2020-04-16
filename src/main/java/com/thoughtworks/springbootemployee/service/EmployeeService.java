package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    public EmployeeService() {}

    public List<Employee> getEmployees() {
        return employeeRepository.getEmployees();
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.getEmployees().stream().filter(employee -> employee.getGender().toLowerCase().equals(gender)).collect(Collectors.toList());
    }

    public Employee getEmployeesById(Integer employeeId) {
        return employeeRepository.getEmployees().stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
    }
}
