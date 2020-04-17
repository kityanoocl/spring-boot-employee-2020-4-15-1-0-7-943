package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.thoughtworks.springbootemployee.service")
@ComponentScan("com.thoughtworks.springbootemployee.repository")
public class EmployeeControllerTest {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeController employeeController;
    @Autowired
    EmployeeService employeeService;

    @Before
    public void initialization() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    @Test
    public void shouldGetAllEmployees() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, employees.size());
        Assert.assertEquals(1, employees.get(0).getId().intValue());
        Assert.assertEquals("Test 1", employees.get(0).getName());
        Assert.assertEquals(20, employees.get(0).getAge());
        Assert.assertEquals("Male", employees.get(0).getGender());
        Assert.assertEquals(2, employees.get(1).getId().intValue());
        Assert.assertEquals("Test 2", employees.get(1).getName());
        Assert.assertEquals(18, employees.get(1).getAge());
        Assert.assertEquals("Female", employees.get(1).getGender());
    }

    @Test
    public void shouldFindEmployeeById() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId().intValue());
        Assert.assertEquals("Test 1", employee.getName());
    }

    @Test
    public void shouldFindEmployeeByGender() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender", "male")
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
        Assert.assertEquals("Test 1", employees.get(0).getName());
    }

    @Test
    public void shouldAddEmployee() {
        Employee employee = new Employee();
        employee.setName("XX");
        employee.setAge(20);
        employee.setGender("Male");
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .post("/employees");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3, employees.size());
        Assert.assertEquals("Test 1", employees.get(0).getName());
        Assert.assertEquals("XX", employees.get(2).getName());
    }

    @Test
    public void shouldUpdateEmployee() {
        Employee employee = new Employee();
        employee.setName("Update");
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .put("/employees/1");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employees.get(0).getId().intValue());
        Assert.assertEquals("Update", employees.get(0).getName());
        Assert.assertEquals(20, employees.get(0).getAge());
        Assert.assertEquals("Male", employees.get(0).getGender());
    }

    @Test
    public void shouldDeleteEmployee() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/1");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals(2, employees.get(0).getId().intValue());
        Assert.assertEquals("Test 2", employees.get(0).getName());
        Assert.assertEquals(18, employees.get(0).getAge());
        Assert.assertEquals("Female", employees.get(0).getGender());
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
        Assert.assertEquals(2, employees.get(0).getId().intValue());
        Assert.assertEquals("Test 2", employees.get(0).getName());
        Assert.assertEquals(18, employees.get(0).getAge());
        Assert.assertEquals("Female", employees.get(0).getGender());
    }
}