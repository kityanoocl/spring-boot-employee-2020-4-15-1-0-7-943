package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class EmployeeFactory {
    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Test 1", 20, "Male", 1));
        employees.add(new Employee(2, "Test 2", 18, "Female", 2));
        return employees;
    }
}
