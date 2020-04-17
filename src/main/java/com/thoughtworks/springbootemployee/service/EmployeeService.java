package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.commonUtil.CommonUtil;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee getEmployeeById(Integer employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public List<Employee> addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return getEmployees();
    }

    public List<Employee> updateEmployeeInfo(Integer employeeId, Employee newEmployeeInfo) {
        Employee employee = getEmployeeById(employeeId);
        if (employee != null) {
            employee.update(newEmployeeInfo);
            employeeRepository.save(employee);
        }
        return getEmployees();
    }

    public List<Employee> deleteEmployee(Integer employeeId) {
        employeeRepository.findById(employeeId).ifPresent(employee -> employeeRepository.delete(employee));
        return getEmployees();
    }

    public List<Employee> getEmployeesInPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }
}
