package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeService() {
    }

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
        }

        return employeeRepository.getEmployees();
    }

    public List<Employee> deleteEmployee(Integer employeeId) {
        Employee employee = getEmployeesById(employeeId);
        employeeRepository.deleteEmployee(employee);
        return employeeRepository.getEmployees();
    }

    public List<Employee> getEmployeesInPage(Integer page, Integer pageSize) {
        List<Employee> employees = getEmployees();
        int employeeSize = employees.size();
        int startIndex = min(employeeSize, (page - 1) * pageSize);
        startIndex = max(0, startIndex);
        int endIndex = max(1, page * pageSize);
        endIndex = min(endIndex, employeeSize);
        return employees.subList(startIndex, endIndex);
    }
}
