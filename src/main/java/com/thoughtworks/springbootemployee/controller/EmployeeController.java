package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAllEmployee() {
        return employees;
    }

    @GetMapping(params = "gender")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Employee> getEmployeesInSameGender(@RequestParam String gender) {
        return employees.stream().filter(employee -> employee.getGender().toLowerCase().equals(gender)).collect(Collectors.toList());
    }

    @GetMapping(params = "page")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Employee> getEmployeesInPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        int employeeSize = employees.size();
        int startIndex = min(employeeSize, (page - 1) * pageSize);
        startIndex = max(0, startIndex);
        int endIndex = max(1, page * pageSize);
        endIndex = min(endIndex, employeeSize);
        return employees.subList(startIndex, endIndex);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Employee getEmployee(@PathVariable Integer employeeId) {
        return employees.stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        if (employees.stream().anyMatch(employeeInList -> employeeInList.getId() == employee.getId())) {
            return null;
        }

        employees.add(employee);
        return employee;
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Employee updateCompanyBasicInfo(@PathVariable Integer employeeId, @RequestBody Employee newEmployeeInfo) {
        Employee employee = employees.stream().filter(companyInList -> companyInList.getId() == employeeId).findFirst().orElse(null);
        if (employee == null) {
            return null;
        }

        employee.setId(newEmployeeInfo.getId());
        employee.setName(newEmployeeInfo.getName());
        employee.setAge(newEmployeeInfo.getAge());
        employee.setGender(newEmployeeInfo.getGender());

        return employee;
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Employee> deleteCompanyEmployees(@PathVariable Integer employeeId) {
        employees = employees.stream().filter(companyInList -> companyInList.getId() != employeeId).collect(Collectors.toList());
        return employees;
    }
}