package com.example.parrollmangmentsystem;

import javafx.scene.image.Image;

import java.util.Date;

public class employeeData {
    private String emails;
    private Double salary;
    private String name;
    private String phones;
    private String gender;
    private String department;
    private String address;
    private Date birthdate;
    private Integer employeeid;
    private String image;

    public employeeData(String emails, Double salary, String name, String phones, String gender, String department, String address, Date birthdate, Integer employeeid, String image) {
        this.emails = emails;
        this.salary = salary;
        this.name = name;
        this.phones = phones;
        this.gender = gender;
        this.department = department;
        this.address = address;
        this.birthdate = birthdate;
        this.employeeid = employeeid;
        this.image = image;
    }

    public String getEmails() {
        return emails;
    }

    public Double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public String getPhones() {
        return phones;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public String getImage() {
        return image;
    }

}
