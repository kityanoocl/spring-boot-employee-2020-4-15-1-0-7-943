package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeFactory;
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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.thoughtworks.springbootemployee.service")
@ComponentScan("com.thoughtworks.springbootemployee.repository")
public class EmployeeControllerTest {
    public List<Employee> employees = new ArrayList<>();
    public Employee employee = new Employee();

    @Mock
    private EmployeeService employeeService;

    @Before
    public void setUp(){
        EmployeeController employeeController = new EmployeeController(employeeService);
        RestAssuredMockMvc.standaloneSetup(employeeController);

        employees = EmployeeFactory.getEmployees();
    }


    @Test
    public void shouldGetAllEmployees() {
        doReturn(employees).when(employeeService).getEmployees();
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
        Employee employee = new Employee(1, "Test 1", 20, "Male", 1);
        doReturn(employee).when(employeeService).getEmployeeById(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Employee employeeFromResponse = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employeeFromResponse.getId().intValue());
        Assert.assertEquals("Test 1", employeeFromResponse.getName());
    }

    @Test
    public void shouldFindEmployeeByGender() {
        employees = employees.stream().filter(employee -> employee.getGender().equals("Male")).collect(Collectors.toList());
        doReturn(employees).when(employeeService).getEmployeesByGender(any());
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
        employees.add(new Employee(3, "XX", 20, "Male", 1));
        doReturn(employees).when(employeeService).addEmployee(any());
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
        employees.get(0).setName("Update");
        employees.get(0).setAge(20);
        employees.get(0).setGender("Male");
        doReturn(employees).when(employeeService).updateEmployeeInfo(any(), any());
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
        employees.remove(0);
        doReturn(employees).when(employeeService).deleteEmployee(any());
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
        employees.remove(0);
        doReturn(employees).when(employeeService).getEmployeesInPage(any(), any());
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