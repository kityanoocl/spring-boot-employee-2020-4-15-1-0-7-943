package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer employeesNumber;
    @OneToMany(targetEntity = Employee.class, mappedBy = "companyId", fetch = FetchType.EAGER)
    private List<Employee> employees = new ArrayList<>();

    public void update(Company newCompanyInfo) {
        this.setName(newCompanyInfo.getName() == null? this.getName() : newCompanyInfo.getName());
        this.setEmployeesNumber(newCompanyInfo.getEmployeesNumber() == null? this.getEmployeesNumber() : newCompanyInfo.getEmployeesNumber());
        this.setEmployees(newCompanyInfo.getEmployees() == null? this.getEmployees() : newCompanyInfo.getEmployees());
    }
}
