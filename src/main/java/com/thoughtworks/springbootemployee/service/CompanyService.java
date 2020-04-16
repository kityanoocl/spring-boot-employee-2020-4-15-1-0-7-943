package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;

import java.util.List;

public class CompanyService {
    private final CompanyRepository companyRepository = new CompanyRepository();

    public CompanyService() {}


    public List<Company> getCompanies() {
        return companyRepository.getCompanies();
    }
}
