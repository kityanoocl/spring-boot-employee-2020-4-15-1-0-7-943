package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyFactory;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService = new CompanyService();
    private List<Company> companies = CompanyFactory.getCompanies();

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping(params = "page")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompaniesInPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return companyService.getCompaniesInPage(page, pageSize);
    }

    @GetMapping("/{companyName}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompany(@PathVariable String companyName) {
        return companies.stream().filter(company -> company.getName().equals(companyName)).findFirst().orElse(null);
    }

    @GetMapping("/{companyName}/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getCompanyEmployees(@PathVariable String companyName) {
        Company company = companies.stream().filter(companyInList -> companyInList.getName().equals(companyName)).findFirst().orElse(null);
        if (company == null) {
            return null;
        }

        return company.getEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> addCompany(@RequestBody Company company) {
        if (companies.stream().anyMatch(companyInList -> companyInList.getName().equals(company.getName()))) {
            return null;
        }

        companies.add(company);
        return companies;
    }

    @PutMapping("/{companyName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> updateCompanyBasicInfo (@PathVariable String companyName, @RequestBody Company newCompanyInfo) {
        Company company = companies.stream().filter(companyInList -> companyInList.getName().equals(companyName)).findFirst().orElse(null);
        if (company == null) {
            return null;
        }

        company.setName(newCompanyInfo.getName());
        company.setEmployeesNumber(newCompanyInfo.getEmployeesNumber());
        company.setEmployees(newCompanyInfo.getEmployees());

        return companies;
    }

    @DeleteMapping("/{companyName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> deleteCompanyEmployees (@PathVariable String companyName) {
        companies = companies.stream().filter(companyInList -> !companyInList.getName().equals(companyName)).collect(Collectors.toList());

        return companies;
    }
}
