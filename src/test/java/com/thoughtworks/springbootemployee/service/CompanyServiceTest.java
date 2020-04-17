package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

public class CompanyServiceTest {
    private CompanyRepository mockCompanyRepository;
    private CompanyService companyService;

    @Before
    public void setUp() {
        mockCompanyRepository = Mockito.mock(CompanyRepository.class);
        companyService = new CompanyService(mockCompanyRepository);
    }

    @Test
    public void shouldUpdateEmployee() {
        Company company = new Company(1, "abc", 100, new ArrayList<>());
        Mockito.when(mockCompanyRepository.findById(1)).thenReturn(Optional.of(company));

        companyService.getCompanyByCompanyId(1);

        Mockito.verify(mockCompanyRepository, Mockito.times(1)).findById(1);
    }
}