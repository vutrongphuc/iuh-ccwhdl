package com.hpt.iuh.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee {
    @Id
    @Column(length = 10)
    private String employee_id;

    @Column(length = 100)
    private String name;

    private boolean sex;

    @Column(length = 255)
    private String email;

    @Column(length = 10)
    private String phone;

    @Column(length = 255)
    private String address;

    @Column(length = 10)
    private String department_id;

    public Employee(String employee_id, String name, boolean sex, String email, String phone, String address, String department_id) {
        super();
        this.employee_id = employee_id;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.department_id = department_id;
    }

    public Employee() {

    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

}
