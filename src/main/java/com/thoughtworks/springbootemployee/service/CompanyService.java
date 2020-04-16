package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class CompanyService {
    private final CompanyRepository companyRepository = new CompanyRepository();

    public CompanyService() {}


    public List<Company> getCompanies() {
        return companyRepository.getCompanies();
    }

    public List<Company> getCompaniesInPage(Integer page, Integer pageSize) {
        List<Company> companies = getCompanies();
        int companySize = companies.size();
        int startIndex = min(companySize, (page - 1) * pageSize);
        startIndex = max(0, startIndex);
        int endIndex = max(1, page * pageSize);
        endIndex = min(endIndex, companySize);
        return companies.subList(startIndex, endIndex);
    }

    public Company getCompanyByCompanyName(String companyName) {
        return getCompanies().stream().filter(company -> company.getName().equals(companyName)).findFirst().orElse(null);
    }

    public List<Employee> getCompanyEmployees(String companyName) {
        Company company = getCompanyByCompanyName(companyName);
        if (company == null) {
            return null;
        }

        return company.getEmployees();
    }

    public List<Company> addCompany(Company company) {
        if (getCompanyByCompanyName(company.getName()) == null) {
            companyRepository.add(company);
        }

        return getCompanies();
    }

    public List<Company> updateCompanyBasicInfo(String companyName, Company newCompanyInfo) {
        Company company = getCompanyByCompanyName(companyName);
        if (company != null) {
            company.setName(newCompanyInfo.getName());
            company.setEmployeesNumber(newCompanyInfo.getEmployeesNumber());
            company.setEmployees(newCompanyInfo.getEmployees());
        }

        return getCompanies();
    }

    public List<Company> deleteCompanyEmployees(String companyName) {
        Company company = getCompanyByCompanyName(companyName);
        if (company != null) {
            company.setEmployees(new ArrayList<Employee>());
        }

        return getCompanies();
    }
}
