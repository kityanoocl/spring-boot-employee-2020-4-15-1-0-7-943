package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyFactory;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

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
    public Company getCompanyByCompanyName(@PathVariable String companyName) {
        return companyService.getCompanyByCompanyName(companyName);
    }

    @GetMapping("/{companyName}/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getCompanyEmployees(@PathVariable String companyName) {
        return companyService.getCompanyEmployees(companyName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping("/{companyName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> updateCompanyBasicInfo(@PathVariable String companyName, @RequestBody Company newCompanyInfo) {
        return companyService.updateCompanyBasicInfo(companyName, newCompanyInfo);
    }

    @DeleteMapping("/{companyName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> deleteCompanyEmployees(@PathVariable String companyName) {
        return companyService.deleteCompanyEmployees(companyName);
    }
}
