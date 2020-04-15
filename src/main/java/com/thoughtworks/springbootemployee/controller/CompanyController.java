package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Company> getCompanies() {
        return companies;
    }

    @GetMapping("/{companyName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Company getCompany(@PathVariable String companyName) {
        return companies.stream().filter(company -> company.getName().equals(companyName)).findFirst().orElse(null);
    }

    @GetMapping("/{companyName}/employees")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Employee> getCompanyEmployees(@PathVariable String companyName) {
        Company company = companies.stream().filter(companyInList -> companyInList.getName().equals(companyName)).findFirst().orElse(null);
        if (company == null) {
            return null;
        }

        return company.getEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        if (companies.stream().anyMatch(companyInList -> companyInList.getName().equals(company.getName()))) {
            return null;
        }

        companies.add(company);
        return company;
    }

    @PutMapping("/{companyName}")
    @ResponseStatus
    public Company updateCompanyBasicInfo (@PathVariable String companyName, @RequestBody Company newCompanyInfo) {
        Company company = companies.stream().filter(companyInList -> companyInList.getName().equals(companyName)).findFirst().orElse(null);
        if (company == null) {
            return null;
        }

        company.setName(newCompanyInfo.getName());
        company.setEmployeesNumber(newCompanyInfo.getEmployeesNumber());
        company.setEmployees(newCompanyInfo.getEmployees());

        return company;
    }

    @DeleteMapping("/{companyName}")
    @ResponseStatus
    public Company deleteCompanyEmployees (@PathVariable String companyName) {
        Company company = companies.stream().filter(companyInList -> companyInList.getName().equals(companyName)).findFirst().orElse(null);
        if (company == null) {
            return null;
        }

        company.setEmployees(new ArrayList<Employee>());

        return company;
    }
}
