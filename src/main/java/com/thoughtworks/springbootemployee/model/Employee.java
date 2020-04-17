package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer companyId;
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "employeeId")
    private ParkingBoy parkingBoy;

    public void update(Employee newEmployeeInfo) {
        this.setName(newEmployeeInfo.getName() == null? this.getName() : newEmployeeInfo.getName());
        this.setAge(newEmployeeInfo.getAge() == null? this.getAge() : newEmployeeInfo.getAge());
        this.setGender(newEmployeeInfo.getGender() == null? this.getGender() : newEmployeeInfo.getGender());
        this.setCompanyId(newEmployeeInfo.getCompanyId() == null? this.getCompanyId() : newEmployeeInfo.getCompanyId());
    }
}