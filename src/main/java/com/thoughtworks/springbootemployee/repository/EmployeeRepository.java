package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeFactory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = EmployeeFactory.getEmployees();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void add(Employee employee) {
        employees.add(employee);
    }

    public void deleteEmployee(Employee employee) {
        employees.remove(employee);
    }

    public void resetEmployees() {
        employees = EmployeeFactory.getEmployees();
    }
}
