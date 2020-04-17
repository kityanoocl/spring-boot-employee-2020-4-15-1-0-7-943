package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

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

    @GetMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyByCompanyId(@PathVariable Integer companyId) {
        return companyService.getCompanyByCompanyId(companyId);
    }

    @GetMapping("/{companyId}/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getCompanyEmployees(@PathVariable Integer companyId) {
        return companyService.getCompanyEmployees(companyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }


    @PutMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompanyBasicInfo(@PathVariable Integer companyId, @RequestBody Company newCompanyInfo) {
        return companyService.updateCompanyBasicInfo(companyId, newCompanyInfo);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> deleteCompany(@PathVariable Integer companyId) {
        return companyService.deleteCompany(companyId);
    }
}
