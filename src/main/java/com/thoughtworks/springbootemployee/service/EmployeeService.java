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

    public List<Employee> addEmployee(Employee employee) {
        if (getEmployeesById(employee.getId()) == null) {
            employeeRepository.add(employee);
        }

        return employeeRepository.getEmployees();
    }

    public List<Employee> updateEmployeeInfo(Integer employeeId, Employee newEmployeeInfo) {
        Employee employee = getEmployeesById(employeeId);
        if (employee != null) {
            employee.setId(newEmployeeInfo.getId());
            employee.setName(newEmployeeInfo.getName());
            employee.setAge(newEmployeeInfo.getAge());
            employee.setGender(newEmployeeInfo.getGender());
            employeeRepository.updateEmployee(employee);
        }
        return employeeRepository.getEmployees();
    }

    public List<Employee> deleteEmployee(Integer employeeId) {
        Employee employee = getEmployeesById(employeeId);
        employeeRepository.deleteEmployee(employee);
        return employeeRepository.getEmployees();
    }
}
