package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class CompanyFactory {
    public static List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        List<Employee> employees1 = new ArrayList<>();
        List<Employee> employees2 = new ArrayList<>();
        employees1.add(new Employee(1, "Test 1", 18, "Male", 1, 1,new ParkingBoy()));
        employees1.add(new Employee(2, "Test 2", 19, "Female", 1, 2, new ParkingBoy()));
        companies.add(new Company(1, "abc", 10, employees1));
        employees2.add(new Employee(1, "Test 3", 20, "Male", 2, 3, new ParkingBoy()));
        companies.add(new Company(2, "def", 30, employees2));
        return companies;
    }
}
