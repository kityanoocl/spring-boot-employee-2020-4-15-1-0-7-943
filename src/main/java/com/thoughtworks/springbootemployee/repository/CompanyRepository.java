package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyFactory;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        companies = CompanyFactory.getCompanies();
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void add(Company company) {
        companies.add(company);
    }
}
