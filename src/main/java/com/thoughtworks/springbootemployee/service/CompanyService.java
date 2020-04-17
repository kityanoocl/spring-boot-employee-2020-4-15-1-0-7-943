package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.commonUtil.CommonUtil;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return companyRepository.findAll();
    }

    public List<Company> getCompaniesInPage(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company getCompanyByCompanyName(String companyName) {
        return companyRepository.findByName(companyName);
    }

    public List<Employee> getCompanyEmployees(String companyName) {
        Company company = getCompanyByCompanyName(companyName);
        if (company == null) {
            return null;
        }

        return company.getEmployees();
    }

    public List<Company> addCompany(Company company) {
        companyRepository.save(company);
        return getCompanies();
    }

    public Company updateCompanyBasicInfo(String companyName, Company newCompanyInfo) {
        Company company = getCompanyByCompanyName(companyName);
        if (company != null) {
            company.update(newCompanyInfo);
            companyRepository.saveAndFlush(company);
        }

        return company;
    }

    public List<Company> deleteCompanyEmployees(String companyName) {
        Company company = getCompanyByCompanyName(companyName);
        if (company != null) {
            company.setEmployees(new ArrayList<Employee>());
            companyRepository.save(company);
        }

        return getCompanies();
    }
}
