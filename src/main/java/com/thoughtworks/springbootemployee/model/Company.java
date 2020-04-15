package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private int employeesNumber;
    private List<Employee> employees = new ArrayList<>();
}
