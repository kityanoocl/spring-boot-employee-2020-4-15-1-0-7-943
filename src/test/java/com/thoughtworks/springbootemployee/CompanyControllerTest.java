package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.*;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.thoughtworks.springbootemployee.service")
@ComponentScan("com.thoughtworks.springbootemployee.Repository")
public class CompanyControllerTest {
    public List<Company> companies = new ArrayList<>();

    @Mock
    private CompanyService companyService;

    @Before
    public void setUp(){
        CompanyController companyController = new CompanyController(companyService);
        RestAssuredMockMvc.standaloneSetup(companyController);

        companies = CompanyFactory.getCompanies();
    }


    @Test
    public void shouldGetAllEmployees() {
        doReturn(companies).when(companyService).getCompanies();
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
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Test 1", 18, "Male", 1, new ParkingBoy()));
        employees.add(new Employee(2, "Test 2", 19, "Female", 1, new ParkingBoy()));
        Company company = new Company(1, "abc", 10, employees);
        doReturn(company).when(companyService).getCompanyByCompanyName(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/abc");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Company companyFromResponse = response.getBody().as(Company.class);
        Assert.assertEquals("abc", companyFromResponse.getName());
        Assert.assertEquals(10, companyFromResponse.getEmployeesNumber());
    }

    @Test
    public void shouldFindCompanyByNameAndGetEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Test 1", 18, "Male", 1, new ParkingBoy()));
        employees.add(new Employee(2, "Test 2", 19, "Female", 1, new ParkingBoy()));
        doReturn(employees).when(companyService).getCompanyEmployees(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/abc/employees");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employeesFromResponse = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, employeesFromResponse.size());
        Assert.assertEquals("Test 1", employeesFromResponse.get(0).getName());
        Assert.assertEquals("Test 2", employeesFromResponse.get(1).getName());
    }

    @Test
    public void shouldAddCompany() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(3, "XX", 84, "Male", 3, new ParkingBoy()));
        Company company = new Company(3, "ghi", 40, employees);
        companies.add(company);
        doReturn(companies).when(companyService).addCompany(any());
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
        employees.add(new Employee(1, "Update", 20, "Male", 1, new ParkingBoy()));
        Company company = new Company(1, "abc", 20, employees);
        companies.get(0).setName("abc");
        companies.get(0).setEmployees(employees);
        companies.get(0).setEmployeesNumber(20);
        doReturn(companies).when(companyService).updateCompanyBasicInfo(any(), any());
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
    public void shouldDeleteCompanyEmployees() {
        companies.get(0).setEmployees(new ArrayList<>());
        doReturn(companies).when(companyService).deleteCompanyEmployees(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/abc");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Company> companiesFromResponse = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, companiesFromResponse.size());
        Assert.assertEquals("abc", companiesFromResponse.get(0).getName());
        Assert.assertEquals(10, companiesFromResponse.get(0).getEmployeesNumber());
        Assert.assertEquals(0, companiesFromResponse.get(0).getEmployees().size());
    }

    @Test
    public void shouldDisplayCompanyInPage() {
        companies.remove(0);
        doReturn(companies).when(companyService).getCompaniesInPage(any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("page", 2, "pageSize", 1)
                .when()
                .get("/companies");

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
}