package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.commonUtil.CommonUtil;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;


    public List<Company> getCompanies() {
        return companyRepository.getCompanies();
    }

    public List<Company> getCompaniesInPage(Integer page, Integer pageSize) {
        List<Company> companies = getCompanies();
        return CommonUtil.returnListInPage(companies, page, pageSize);
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
            company.update(newCompanyInfo);
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
