package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees = EmployeeFactory.getEmployees();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void add(Employee employee) {
        employees.add(employee);
    }

    public void updateEmployee(Employee employee) {
        employees.remove(employee);
        employees.add(employee);
        employees.sort(Comparator.comparingInt(Employee::getId));
    }

    public void deleteEmployee(Employee employee) {
        employees.remove(employee);
    }
}
