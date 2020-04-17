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
    private int age;
    private String gender;
    private Integer companyId;
    @OneToOne
    @JoinColumn(name = "id")
    private ParkingBoy parkingBoy;

    public void update(Employee newEmployeeInfo) {
        this.name = newEmployeeInfo.getName();
        this.age = newEmployeeInfo.getAge();
        this.gender = newEmployeeInfo.getGender();
    }
}