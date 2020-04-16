package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
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
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    @Test
    public void shouldFindEmployeeById() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId());
        Assert.assertEquals("Test 1", employee.getName());
    }

    @Test
    public void shouldFindEmployeeByGendar() {
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
        Employee employee = new Employee(3, "XX", 84, "Male");
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
        Employee employee = new Employee(1, "Update", 20, "Male");
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
        Assert.assertEquals(1, employees.get(0).getId());
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
        Assert.assertEquals(2, employees.get(0).getId());
        Assert.assertEquals("Test 2", employees.get(0).getName());
        Assert.assertEquals(18, employees.get(0).getAge());
        Assert.assertEquals("Female", employees.get(0).getGender());
    }
}