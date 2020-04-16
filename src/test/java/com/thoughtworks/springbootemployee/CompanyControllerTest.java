package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @Before
    public void initialization() throws Exception {
        RestAssuredMockMvc.standaloneSetup(companyController);
    }

    @Test
    public void shouldGetAllEmployees() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
                                                            @Override
                                                            public Type getType() {
                                                                return super.getType();
                                                            }
                                                        });

        Assert.assertEquals(2, companies.size());
        Assert.assertEquals("abc", companies.get(0).getName());
        Assert.assertEquals(10, companies.get(0).getEmployeesNumber());
        Assert.assertEquals(2, companies.get(0).getEmployees().size());
        Assert.assertEquals("def", companies.get(1).getName());
        Assert.assertEquals(30, companies.get(1).getEmployeesNumber());
        Assert.assertEquals(1, companies.get(1).getEmployees().size());
    }

    @Test
    public void shouldFindCompanyByName() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/abc");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Assert.assertEquals("abc", company.getName());
        Assert.assertEquals(10, company.getEmployeesNumber());
    }

    @Test
    public void shouldFindCompanyByNameAndGetEmployees () {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/abc/employees");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, employees.size());
        Assert.assertEquals("Test 1", employees.get(0).getName());
        Assert.assertEquals("Test 2", employees.get(1).getName());
    }

    @Test
    public void shouldAddCompany() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(3, "XX", 84, "Male"));
        Company company = new Company("ghi", 40, employees);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(company)
                .when()
                .post("/companies");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3, companies.size());
        Assert.assertEquals("abc", companies.get(0).getName());
        Assert.assertEquals("ghi", companies.get(2).getName());
    }

    @Test
    public void shouldUpdateCompany() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Update", 20, "Male"));
        Company company = new Company("abc", 20, employees);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(company)
                .when()
                .put("/companies/abc");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals("abc", companies.get(0).getName());
        Assert.assertEquals(20, companies.get(0).getEmployeesNumber());
        Assert.assertEquals("Update", companies.get(0).getEmployees().get(0).getName());
    }

    @Test
    public void shouldDeleteCompany() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/abc");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, companies.size());
        Assert.assertEquals("def", companies.get(0).getName());
        Assert.assertEquals(30, companies.get(0).getEmployeesNumber());
    }

    @Test
    public void shouldDisplayEmployeeInPage() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("page", 2, "pageSize", 1)
                .when()
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals(2, employees.get(0).getId());
        Assert.assertEquals("Test 2", employees.get(0).getName());
        Assert.assertEquals(18, employees.get(0).getAge());
        Assert.assertEquals("Female", employees.get(0).getGender());
    }
}