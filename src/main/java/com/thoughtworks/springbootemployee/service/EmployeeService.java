package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.commonUtil.CommonUtil;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Employee getEmployeeById(Integer employeeId) {
        return employeeRepository.getEmployees().stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
    }

    public List<Employee> addEmployee(Employee employee) {
        if (getEmployeeById(employee.getId()) == null) {
            employeeRepository.add(employee);
        }

        return employeeRepository.getEmployees();
    }

    public List<Employee> updateEmployeeInfo(Integer employeeId, Employee newEmployeeInfo) {
        Employee employee = getEmployeeById(employeeId);
        if (employee != null) {
            employee.update(newEmployeeInfo);
        }

        return employeeRepository.getEmployees();
    }

    public List<Employee> deleteEmployee(Integer employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employeeRepository.deleteEmployee(employee);
        return employeeRepository.getEmployees();
    }

    public List<Employee> getEmployeesInPage(Integer page, Integer pageSize) {
        List<Employee> employees = getEmployees();
        return CommonUtil.returnListInPage(employees, page, pageSize);
    }
}
