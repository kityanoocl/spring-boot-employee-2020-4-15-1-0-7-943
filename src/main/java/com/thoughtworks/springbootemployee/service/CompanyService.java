package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;

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
}
