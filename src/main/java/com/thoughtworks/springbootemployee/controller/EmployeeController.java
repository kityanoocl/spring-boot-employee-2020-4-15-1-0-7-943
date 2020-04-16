package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeFactory;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = EmployeeFactory.getEmployees();
    private final EmployeeService employeeService = new EmployeeService();
    @GetMapping
    public List<Employee> getAllEmployee() {
        return employeeService.getEmployees();
    }

    @GetMapping(params = "gender")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesInSameGender(@RequestParam String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesInPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        int employeeSize = employees.size();
        int startIndex = min(employeeSize, (page - 1) * pageSize);
        startIndex = max(0, startIndex);
        int endIndex = max(1, page * pageSize);
        endIndex = min(endIndex, employeeSize);
        return employees.subList(startIndex, endIndex);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@PathVariable Integer employeeId) {
        return employees.stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> addEmployee(@RequestBody Employee employee) {
        if (employees.stream().anyMatch(employeeInList -> employeeInList.getId() == employee.getId())) {
            return null;
        }

        employees.add(employee);
        return employees;
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> updateCompanyBasicInfo(@PathVariable Integer employeeId, @RequestBody Employee newEmployeeInfo) {
        Employee employee = employees.stream().filter(companyInList -> companyInList.getId() == employeeId).findFirst().orElse(null);
        if (employee == null) {
            return null;
        }

        employee.setId(newEmployeeInfo.getId());
        employee.setName(newEmployeeInfo.getName());
        employee.setAge(newEmployeeInfo.getAge());
        employee.setGender(newEmployeeInfo.getGender());

        return employees;
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> deleteCompanyEmployees(@PathVariable Integer employeeId) {
        employees = employees.stream().filter(companyInList -> companyInList.getId() != employeeId).collect(Collectors.toList());
        return employees;
    }
}