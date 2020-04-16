package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    public EmployeeService() {}

    public List<Employee> getEmployees() {
        return employeeRepository.getEmployees();
    }
}
