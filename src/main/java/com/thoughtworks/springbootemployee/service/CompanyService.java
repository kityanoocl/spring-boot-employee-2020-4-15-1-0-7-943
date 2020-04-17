package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getCompaniesInPage(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company getCompanyByCompanyId(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Employee> getCompanyEmployees(Integer companyId) {
        Company company = getCompanyByCompanyId(companyId);
        if (company == null) {
            return null;
        }

        return company.getEmployees();
    }

    public List<Company> addCompany(Company company) {
        companyRepository.save(company);
        return getCompanies();
    }

    public Company updateCompanyBasicInfo(Integer companyId, Company newCompanyInfo) {
        Company company = getCompanyByCompanyId(companyId);
        if (company != null) {
            company.update(newCompanyInfo);
            companyRepository.saveAndFlush(company);
        }

        return company;
    }


    public List<Company> deleteCompany(Integer companyId) {
        companyRepository.findById(companyId).ifPresent(company -> companyRepository.delete(company));
        return getCompanies();
    }
}
